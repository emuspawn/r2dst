package connection;

import graphics.Camera;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

import network.Lock;

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
		
		cli.writeInt(1);
		cli.writeByte(km.getUserActionByte(c));
		cli.writeByte(Byte.parseByte(index+""));

		cli.flush();
		lck.releaseLock();
	}
	public ArrayList<Element> getVisibleElements()
	{
		lck.acquireLock();
		
		cli.writeInt(2);
		cli.writeInt(index);
		
		cli.flush();
		
		lck.releaseLock();
		
		ArrayList<Element> els = new ArrayList<Element>();
		int elementCount = getIntResponse();
		for (int i = 0; i < elementCount; i++)
		{	
			int type = getIntResponse();
			String name = getStringResponse();
			Location loc = new Location(getDoubleResponse(), getDoubleResponse());
			int width = getIntResponse();
			int height = getIntResponse();
			
			switch (type)
			{
			case 1:
				//System.out.println("Making new Unit");
				els.add(new Unit(name, loc, width, height));
				break;
				
			case 2:
				//System.out.println("Making new Dirt");
				els.add(new Dirt(loc));
				break;
				
			case 3:
				//System.out.println("Making new Stone");
				els.add(new HardStone(loc));
				break;
				
			default:
				System.out.println("BAD TYPE!!!");
				while(true);
			}
		}
		
		System.out.println("getVisibleUnits()");
		
		return els;
	}
	public Camera getCamera()
	{	
		lck.acquireLock();
		
		cli.writeInt(3);
		cli.writeInt(index);
		
		cli.flush();
		
		lck.releaseLock();
		
		System.out.println("getCamera()");
		
		
		int width = getIntResponse(), height = getIntResponse(), x = getIntResponse(), y = getIntResponse();
		
		Camera newCam = new Camera(width, height);
		newCam.centerOn(new Location(x, y));
		
		throw new IllegalArgumentException();
		
		//return newCam;
	}
	public void setIndex(int setter)
	{
		index = setter;
	}
	public void sendScreenDimensions(int width, int height)
	{
		lck.acquireLock();
		
		cli.writeInt(0);
		cli.writeInt(index);
		cli.writeInt(width);
		cli.writeInt(height);
		
		cli.flush();
		lck.releaseLock();
	}
	private Integer getIntResponse()
	{
		Integer read = null;
		
		lck.acquireLock();
		while ((read = cli.readInt()) == null) Thread.yield();
		lck.releaseLock();
		
		return read;
	}
	private Double getDoubleResponse()
	{
		Double read = null;
		
		lck.acquireLock();
		while ((read = cli.readDouble()) == null) Thread.yield();
		lck.releaseLock();
		
		return read;
	}
	private String getStringResponse()
	{
		String read = null;
		
		lck.acquireLock();
		while ((read = cli.readString()) == null) Thread.yield();
		lck.releaseLock();
		
		return read;
	}
	
	private void requestConnection()
	{
		lck.acquireLock();
		
		cli.writeInt(4);
		cli.writeString(userName);
		
		cli.flush();
		lck.releaseLock();
		
		setIndex(getIntResponse());
	}
}
