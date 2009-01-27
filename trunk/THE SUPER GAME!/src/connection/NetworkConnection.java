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
		NetworkPacket recvPacket = (NetworkPacket)getResponse();
		Object[][] networkData = (Object[][])recvPacket.data;
		for (int i = 0; i < networkData.length; i++)
		{	
			Object[] data = networkData[i];
			
			Location loc = new Location((Double)data[2], (Double)data[3]);
			
			switch ((Integer)data[0])
			{
			case 1:
				els.add(new Unit((String)data[1], loc, (Integer)data[4], (Integer)data[5]));
				break;
				
			case 2:
				els.add(new Dirt(loc));
				break;
				
			case 3:
				els.add(new HardStone(loc));
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
		//throw new IllegalArgumentException();
	}
	
	private Object getResponse()
	{
		Object read = null;
		
		lck.acquireLock();
		try {
			while ((read = cli.readObject()) == null) Thread.yield();
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
