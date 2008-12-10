import java.util.ArrayList;
import java.util.LinkedList;
import java.io.IOException;
import java.net.*;


public class UDP_Connection extends Thread {
	private LinkedList<DatagramPacket> datagrams;
	private ArrayList<SocketAddress> connections;
	private DatagramSocket sock;
	private int maxBufferSize;
	
	private final byte cmdNormal = 0;
	private final byte cmdConnect = 1;
	private final byte cmdDisconnect = 2;
	
	public UDP_Connection(int port, int maxData) throws SocketException
	{
		sock = new DatagramSocket(port);
		connections = new ArrayList<SocketAddress>();
		datagrams = new LinkedList<DatagramPacket>();
		maxBufferSize = maxData;
		
		start();
	}
	
	public boolean connectTo(InetAddress addr, int port)
	{
		byte[] buffer = new byte[maxBufferSize];
		DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, addr, port);
		setupPacket(datagram, cmdConnect);
		try {
			sock.send(datagram);
			connections.add(datagram.getSocketAddress());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean disconnect(InetAddress addr, int port)
	{
		byte[] buffer = new byte[maxBufferSize];
		DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, addr, port);
		setupPacket(datagram, cmdDisconnect);
		try {
			sock.send(datagram);
			connections.remove(datagram.getSocketAddress());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<SocketAddress> getConnections()
	{
		return new ArrayList<SocketAddress>(connections);
	}
	
	public void run()
	{
		while (!isInterrupted())
		{
			DatagramPacket datagram = new DatagramPacket(new byte[maxBufferSize], maxBufferSize);
			try {
				sock.receive(datagram);
			} catch (IOException e) { 
				e.printStackTrace();
				return; 
			}
			
			if (datagram.getData()[0] == cmdConnect)
			{
				connections.add(datagram.getSocketAddress());
			}
			else if (datagram.getData()[0] == cmdDisconnect)
			{
				connections.remove(datagram.getSocketAddress());
			}
			else
			{
				datagrams.add(datagram);
			}
		}
	}
	
	public byte[] recv(boolean block)
	{
		byte[] data = null, newData = new byte[maxBufferSize];
		if (!datagrams.isEmpty())
		{
			data = datagrams.poll().getData();
			
			for (int i = 1; i < data.length; i++)
				newData[i-1] = data[i];
		} 
		else if (block)
		{
			while (datagrams.isEmpty());
			
			data = datagrams.poll().getData();
			
			for (int i = 1; i < data.length; i++)
				newData[i-1] = data[i];
		}
		
		return newData;
	}
	
	public boolean send(InetAddress addr, int port, byte[] buffer)
	{
		DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, addr, port);
		
		try {
			setupPacket(datagram, cmdNormal);
			sock.send(datagram);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	private void setupPacket(DatagramPacket datagram, byte packetFlag)
	{
		byte[] newData = new byte[datagram.getData().length + 1];
		
		newData[0] = packetFlag;
		
		for (int i = 1; i < newData.length; i++)
		{
			newData[i] = datagram.getData()[i-1];
		}
		
		datagram.setData(newData);
	}
}
