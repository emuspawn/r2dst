package connection;

import graphics.Camera;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

import network.Lock;
import network.NetworkPacket;

import permanents.terrain.Dirt;
import permanents.terrain.HardStone;

import utilities.Location;
import world.Element;
import world.destructable.Unit;

import TCPv2.*;

public class NetworkConnection extends Connection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	KeyMap km;
	String userName;
	
	TCP_Client cli;
	
	Thread thrd;
	
	Lock lck = new Lock();
	
	public NetworkConnection(InetAddress addr, int port, String uName)
	{
		userName = uName;
		try
		{
			cli = new TCP_Client(addr, port);
			
			requestConnection();
			
			File f = new File(System.getProperty("user.dir")+"\\keyMapV1.km");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			km = new KeyMap(dis);
		}
		catch(IOException e)
		{
			System.out.println("io exception");
		}
	}
	public String getUserName()
	{
		return userName;
	}
	public void relayUserAction(char c)
	{
		lck.acquireLock();
		NetworkPacket pack = new NetworkPacket();
		
		pack.type = 1;
		pack.data = new Object[] {km.getUserActionByte(c), Byte.parseByte(index+"")};
		
		//System.out.println("Sending relayUserAction Packet");
		
		cli.writeObject(pack);
		cli.flush();
		lck.releaseLock();
	}
	public ArrayList<Element> getVisibleElements()
	{
		lck.acquireLock();
		NetworkPacket pack = new NetworkPacket();
		
		pack.type = 2;
		pack.data = new Object[] {index};
		
		//System.out.println("Sending getVisible Packet");
		
		cli.writeObject(pack);
		cli.flush();
		
		lck.releaseLock();
		
		ArrayList<Element> els = new ArrayList<Element>();
		NetworkPacket recvPacket;
		for (;;)
		{
			recvPacket = (NetworkPacket)getResponse();
			
			if (recvPacket.type == -1 && recvPacket.data == null)
			{
				//System.out.println("Got end packet");
				break;
			}
			
			switch ((Integer)recvPacket.data[0])
			{
			case 1:
				els.add(new Unit((String)recvPacket.data[1], (Location)recvPacket.data[2], (Integer)recvPacket.data[3], (Integer)recvPacket.data[4]));
				break;
				
			case 2:
				els.add(new Dirt((Location)recvPacket.data[2]));
				break;
				
			case 3:
				els.add(new HardStone((Location)recvPacket.data[2]));
				break;
				
			default:
				System.out.println("BAD TYPE!!!");
				while(true);
			}
		}
		
		return els;
	}
	public Camera getCamera()
	{	
		lck.acquireLock();
		
		NetworkPacket pack = new NetworkPacket();
		
		pack.type = 3;
		pack.data = new Object[] {index};
		
		//System.out.println("Sending getCamera packet");
		
		cli.writeObject(pack);
		cli.flush();
		
		lck.releaseLock();
		
		Integer[] ints = (Integer[])getResponse();
		
		Camera newCam = new Camera(ints[0], ints[1]);
		newCam.centerOn(new Location(ints[2]+(ints[0]/2), ints[3]+(ints[1]/2)));
		
		return newCam;
	}
	public void setIndex(int setter)
	{
		index = setter;
	}
	public void sendScreenDimensions(int width, int height)
	{
		lck.acquireLock();
		NetworkPacket pack = new NetworkPacket();
		
		pack.type = 0;
		pack.data = new Object[] {index, width, height};
		
		cli.writeObject(pack);
		cli.flush();
		lck.releaseLock();
	}
	
	private Object getResponse()
	{
		Object read = null;
		
		lck.acquireLock();
		try {
			while ((read = cli.readObject()) == null);
		} catch (ClassNotFoundException e) {
			read = null;
		}
		lck.releaseLock();
		
		return read;
	}
	
	private void requestConnection()
	{
		lck.acquireLock();
		NetworkPacket pack = new NetworkPacket();
		
		pack.type = 4;
		pack.data = new Object[] {userName};
		
		cli.writeObject(pack);
		cli.flush();
		lck.releaseLock();
		
		setIndex((Integer)getResponse());
	}
}
