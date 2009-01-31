package connection;

import graphics.Camera;

import java.awt.Color;
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

import TCPv3.*;

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
		cli.writeInt(((Byte)km.getUserActionByte(c)).intValue());
		cli.writeInt(index);

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
			int shapeType = getIntResponse();
			int R = getIntResponse(), G = getIntResponse(), B = getIntResponse();
			boolean impassable = getIntResponse() == 1 ? true : false;
			Location loc = new Location(getIntResponse(), getIntResponse());
			int width = getIntResponse();
			int height = getIntResponse();
			
			els.add(new Element(shapeType, new Color(R, G, B), impassable, loc, width, height));
		}
		
		return els;
	}
	public Camera getCamera()
	{	
		lck.acquireLock();
		
		cli.writeInt(3);
		cli.writeInt(index);
		
		cli.flush();
		
		lck.releaseLock();
		
		int width = getIntResponse(), height = getIntResponse(), x = getIntResponse(), y = getIntResponse();
		
		Camera newCam = new Camera(width, height);
		newCam.centerOn(new Location(x, y));
		
		return newCam;
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
	private int getIntResponse()
	{
		return cli.readInt();
	}
	
	private void requestConnection()
	{
		lck.acquireLock();
		
		cli.writeInt(4);
		//cli.writeString(userName);
		
		cli.flush();
		lck.releaseLock();
		
		setIndex(getIntResponse());
	}
}
