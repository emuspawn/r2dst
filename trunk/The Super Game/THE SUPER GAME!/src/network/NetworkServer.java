package network;

import graphics.Camera;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;


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
	
	private DatagramPacket buildPlayerPacket(SocketAddress target)
	{
		ArrayList<Unit> players = wrld.getPlayers();
		byte[] data = new byte[(players.size()*2)+4];
		
		Camera cam = wrld.getUserCamera(9); //HACK
		
		data[0] = ((Integer)cam.getWidth()).byteValue();
		data[1] = ((Integer)cam.getHeight()).byteValue();
		data[2] = ((Integer)cam.getxover()).byteValue();
		data[3] = ((Integer)cam.getyover()).byteValue();
		
		for (int i = 4; i < players.size(); i+=2)
		{
			data[i] = ((Double)players.get(i-4).getLocation().x).byteValue();
			data[i+1] = ((Double)players.get(i-4).getLocation().y).byteValue();
		}
		
		return buildPacket((byte)0, data, target);
	}
	
	private DatagramPacket buildPacket(byte packetType, byte[] dataBuffer, SocketAddress target)
	{
		byte[] data = new byte[dataBuffer.length+1];
		
		for (int i = 0; i < data.length-1; i++)
		{
			data[i+1] = data[i];
		}
		
		data[0] = packetType;
		
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
