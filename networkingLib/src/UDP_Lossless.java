import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class UDP_Lossless extends Thread {
	private DatagramSocket sock;
	private LinkedList<byte[]> dataList;
	private ArrayList<DatagramPacket> pendingList, recvList;
	private ArrayList<SocketAddress> peers;
	private int maxData;
	private int ackTimeout;
	
	private UDP_Lock listLock;
	
	//Packet flags
	private final static byte flagNormal = 0;
	private final static byte flagAck = 1;
	private final static byte flagResend = 2;
	private final static byte cmdConnect = 3;
	private final static byte cmdDisconnect = 4;
	
	private final static boolean debug = true;
	private final static boolean testResend = false;
	
	public UDP_Lossless(int port, int maxDatagramSize) throws SocketException
	{
		listLock = new UDP_Lock(false);
		sock = new DatagramSocket(port);	
		dataList = new LinkedList<byte[]>();
		pendingList = new ArrayList<DatagramPacket>();
		recvList = new ArrayList<DatagramPacket>();
		peers = new ArrayList<SocketAddress>();
		maxData = maxDatagramSize;
		ackTimeout = 1500;
		
		start();
	}
	
	public boolean connectTo(InetAddress addr, int port)
	{
		byte[] buffer = new byte[maxData];
		buffer[0] = cmdConnect;
		DatagramPacket datagram = new DatagramPacket(buffer, 1, addr, port);
		
		if (sendDatagram(datagram))
		{
			peers.add(datagram.getSocketAddress());
			return true;
		}
		
		return false;
	}
	
	public boolean disconnect(InetAddress addr, int port)
	{
		byte[] buffer = new byte[maxData];
		buffer[0] = cmdDisconnect;
		DatagramPacket datagram = new DatagramPacket(buffer, 1, addr, port);
		
		if (sendDatagram(datagram))
		{
			peers.remove(addr);
			return true;
		}
		
		return false;
	}
	
	public ArrayList<SocketAddress> getPeerAddresses()
	{
		return new ArrayList<SocketAddress>(peers);
	}
	
	public void setAckTimeout(int timeout)
	{
		ackTimeout = timeout;
	}
	
	public void run()
	{
		while (!isInterrupted())
		{
			DatagramPacket datagram = new DatagramPacket(new byte[maxData+1], maxData+1);
			
			try {
				sock.receive(datagram);
				
				dumpPacket(datagram);
				
				if (datagram.getData()[0] == flagNormal)
				{
					if (datagram.getData().length > 1)
					{
						if (datagram.getData()[1] == cmdConnect)
						{
							listLock.acquire();
							peers.add(datagram.getSocketAddress());
							recvList.add(datagram);
							listLock.release();
						}
						else if (datagram.getData()[1] == cmdDisconnect)
						{
							listLock.acquire();
							peers.remove(datagram.getSocketAddress());
							recvList.add(datagram);
							listLock.release();
						}
						else
						{
							listLock.acquire();
							dataList.add(datagram.getData());
							recvList.add(datagram);
							listLock.release();
						}
					}
					
					setupPacket(datagram, flagAck, true);
					if (!testResend) sock.send(datagram);
				}
				else if (datagram.getData()[0] == flagAck)
				{
					listLock.acquire();
					for (DatagramPacket dgram : pendingList)
					{
						if (dataEqual(datagram, dgram))
						{
							pendingList.remove(dgram);
							break;
						}
					}
					listLock.release();
				}
				else if (datagram.getData()[0] == flagResend)
				{
					boolean sentNewAck = false;
					listLock.acquire();
					
					for (DatagramPacket dgram : recvList)
					{
						if (dataEqual(datagram, dgram))
						{
							setupPacket(datagram, flagAck, true);
							sock.send(datagram);
							sentNewAck = true;
						}
					}
					
					listLock.release();
					
					//We have never seen this packet so we handle it as a new packet
					if (!sentNewAck)
					{
						if (datagram.getData().length > 1)
						{
							if (datagram.getData()[1] == cmdConnect)
							{
								listLock.acquire();
								peers.add(datagram.getSocketAddress());
								recvList.add(datagram);
								listLock.release();
							}
							else if (datagram.getData()[1] == cmdDisconnect)
							{
								listLock.acquire();
								peers.remove(datagram.getSocketAddress());
								recvList.add(datagram);
								listLock.release();
							}
							else
							{
								listLock.acquire();
								dataList.add(datagram.getData());
								recvList.add(datagram);
								listLock.release();
							}
						}
					}
				}
			} catch (IOException e) {}
		}
	}
	
	//Receives a UDP datagram
	public byte[] receiveDatagram(boolean block)
	{
		byte[] buff = null;
		listLock.acquire();
		if (dataList.isEmpty())
		{
			listLock.release();
			if (block) 
			{
				while (true)
				{
					listLock.acquire();
					if (!dataList.isEmpty())
					{
						buff = dataList.poll();
						listLock.release();
						break;
					}
					listLock.release();
				}
			}
			return buff;
		}
		buff = dataList.poll();
		listLock.release();
		return buff;
	}
	
	//Sends a UDP datagram
	public boolean sendDatagram(InetAddress addr, int port, byte[] buffer)
	{
		DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, addr, port);
		return sendDatagram(datagram);
	}
	
	public boolean sendDatagram(DatagramPacket datagram)
	{	
		setupPacket(datagram, flagNormal, false);

		//dumpPacket(datagram);
		
		try {
			listLock.acquire();
			pendingList.add(datagram);
			listLock.release();
			
			sock.send(datagram);

			long startTime = System.currentTimeMillis();
			
			while (true)
			{
				if (System.currentTimeMillis() - startTime >= ackTimeout)
				{
					DbgPrint("Resending packet");
					setupPacket(datagram, flagResend, true);
					sock.send(datagram);
					startTime = System.currentTimeMillis();
				}
				
				listLock.acquire();
				if (pendingList.indexOf(datagram) == -1)
				{
					listLock.release();
					break;
				}
				listLock.release();
			}
			
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	private void setupPacket(DatagramPacket datagram, byte packetFlag, boolean flagPresent)
	{
		int i = 0;
		byte[] newData = new byte[flagPresent ? datagram.getData().length : datagram.getData().length+1];
		
		//Set the packet flag
		newData[i++] = packetFlag;
		
		for (; i < newData.length; i++)
		{
			newData[i] = datagram.getData()[flagPresent ? i : i-1];
		}
		
		datagram.setData(newData);
	}
	
	private void dumpPacket(DatagramPacket datagram)
	{	
		byte[] data = datagram.getData();
		
		System.out.println("-------- Begin packet dump --------");
		System.out.println("Source address: "+datagram.getAddress());
		System.out.println("Port: "+datagram.getPort());
		System.out.println("Packet flag: "+data[0]);
		
		System.out.println("Dumping packet data:");
		
		for (int i = 1; i < data.length; i++)
			System.out.print(data[i]);
		
		System.out.println();
		
		System.out.println("-------- End packet dump --------");
	}
	
	private boolean dataEqual(DatagramPacket datagram1, DatagramPacket datagram2)
	{
		if (datagram1.getData().length != datagram2.getData().length)
		{
			DbgPrint("Bad length 1:"+datagram1.getData().length+" 2:"+datagram2.getData().length);
			return false;
		}
		
		for (int i = 1; i < datagram1.getData().length; i++)
		{
			if (datagram1.getData()[i] != datagram2.getData()[i])
				return false;
		}
		
		return true;
	}
	
	public void DbgPrint(String str)
	{
		if (debug)
			System.out.println(str);
	}
}

class UDP_Lock {
	private boolean lockState;
	
	public UDP_Lock(boolean initialState)
	{
		lockState = initialState;
	}
	
	public void waitForRelease()
	{
		while(lockState);
	}
	
	public void release()
	{
		lockState = false;
	}
	
	public void acquire()
	{
		waitForRelease();
		lockState = true;
	}
}
