package network;

import graphics.Camera;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;


import utilities.ByteIntConverter;
import world.Element;
import world.World;
import world.destructable.Unit;
import TCPv3.TCP_Server;
import UDP.UDP_Connection;
class UDPThread extends Thread {
	UDP_Connection conn;
	World wrld;
	ArrayList<SocketAddress> clients;
	Lock lck;
	
	public void updateClientList(SocketAddress toAdd)
	{
		lck.acquireLock();
		clients.add(toAdd);
		lck.releaseLock();
	}
	public UDPThread(UDP_Connection conn, World wrld)
	{
		this.clients = new ArrayList<SocketAddress>();
		this.conn = conn;
		this.wrld = wrld;
		this.lck = new Lock();
		start();
	}
	
	public void run()
	{
		while (!isInterrupted())
		{
			lck.acquireLock();
			for (int i = 0; i < clients.size(); i++)
			{
				//System.out.println(clients.get(i));
				conn.sendDatagram(buildPlayerPacket(clients.get(i)));
			}
			lck.releaseLock();
		}
	}
	
	private int addBytes(byte[] buff, int index, int num)
	{
		int i = index, k = 0;
		byte[] numByte = ByteIntConverter.intToByteArray(num);
		
		buff[i] = (byte) numByte.length;
		for (i++; k < numByte.length; i++, k++)
		{
			buff[i] = numByte[k];
		}
		
		return i;
	}
	
	private DatagramPacket buildPlayerPacket(SocketAddress target)
	{
		ArrayList<Unit> players = wrld.getPlayers();
		byte[] data = new byte[(players.size()*2)+500];
		
		Camera cam = wrld.getUserCamera(9); //HACK
		
		int offset = 0;
		
		offset += addBytes(data, 1 + offset, cam.getWidth());
		offset += addBytes(data, 2 + offset, cam.getHeight());
		offset += addBytes(data, 3 + offset, cam.getxover());
		offset += addBytes(data, 4 + offset, cam.getyover());
		
		int i = 5 + offset;
		for (int j = 0; j < players.size(); j++)
		{
			data[i] = ((Double)players.get(j).getLocation().x).byteValue();
			data[i+1] = ((Double)players.get(j).getLocation().y).byteValue();
			i++;
		}
		
		data[0] = (byte)i;
		
		return buildPacket((byte)0, data, target);
	}
	
	private DatagramPacket buildPacket(byte packetType, byte[] dataBuffer, SocketAddress target)
	{
		byte[] data = dataBuffer; 
		
		try {
			return new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 1165); //HACK
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
public class NetworkServer extends Thread {
	TCP_Server serv;
	World wrld;
	UDPThread udpThread;
	
	public NetworkServer(int port, World wld) throws IOException
	{
		serv = new TCP_Server(port);
		wrld = wld;
		udpThread = new UDPThread(new UDP_Connection(1164), wrld);
		start();
	}
	
	private Integer getIntResponse(int client)
	{
		Integer read = null;
		
		while ((read = serv.readInt(client)) == null) Thread.yield();
		
		return read;
	}
	
	public void run()
	{
		int lastClientCount = 0;
		
		while (serv.getClientCount() == 0) Thread.yield();
		
		while (!isInterrupted())
		{
			int diff;
			if ((diff = serv.getClientCount() - lastClientCount) > 0)
			{
				System.out.println(diff + " new client(s) connected");
				lastClientCount += diff;
			}
			
			for (int i = 0; i < serv.getClientCount(); i++)
			{
				Integer type = serv.readInt(i);
					
				if (type != null)
				{
					System.out.println("Got packet with type: " +type);
					
					switch (type)
					{
					case 0:
						wrld.updateUserScreenDimensions(getIntResponse(i), getIntResponse(i), getIntResponse(i));
						break;
						
					case 1:
						wrld.interpretUserAction(getIntResponse(i).byteValue(), getIntResponse(i).byteValue());
						break;
						
					case 4:
						serv.writeInt(i, (Integer)wrld.formConnection("test"));
						serv.flush(i);
						
						while (getIntResponse(i) != 10);
						
						serv.writeInt(i, 10);
						serv.writeFile(i, new File(wrld.getLoadPath()));
						
						serv.flush(i);
						
						udpThread.updateClientList(serv.getClientAddress(i));
						break;
					default:
						System.out.println("Bad type: "+type);
						while(true);
					}
				}
			}
		}
	}
}
