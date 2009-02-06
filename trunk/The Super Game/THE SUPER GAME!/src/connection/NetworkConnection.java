package connection;

import graphics.Camera;

import io.WorldReader;

import java.awt.Color;
import java.awt.Point;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

import network.Lock;

import permanents.terrain.Dirt;
import permanents.terrain.HardStone;

import utilities.ByteIntConverter;
import utilities.Location;
import world.Element;
import world.World;
import world.destructable.Unit;

import TCPv3.*;
import UDP.UDP_Connection;

public class NetworkConnection extends Connection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	KeyMap km;
	String userName;
	
	TCP_Client cli;
	UDP_Connection udp;
	
	Thread thrd;
	
	Lock lck = new Lock();
	
	ArrayList<Element> mapElements;
	
	int width, height;
	
	public NetworkConnection(InetAddress addr, int port, String uName)
	{
		userName = uName;
		mapElements = new ArrayList<Element>();
		try
		{
			cli = new TCP_Client(addr, port);
			requestConnection();
			udp = new UDP_Connection(port+1);
			
			File f = new File("keyMapV1.km");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			km = new KeyMap(dis);
		}
		catch(IOException e)
		{
			e.printStackTrace();
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
		ArrayList<Element> els = new ArrayList<Element>();
		DatagramPacket visPack = udp.receiveDatagram(1000);
		byte[] buff = visPack.getData(), tmpBuf;
		
		int width = 0, height = 0, xover = 0, yover = 0;
		int i = 2, tmp = 1;
		
		tmpBuf = new byte[buff[tmp]];
		for (int k = 0; k < buff[tmp]; i++, k++)
		{
			tmpBuf[k] = buff[i];
		}
		width = ByteIntConverter.byteArrayToInt(tmpBuf);
		
		tmp = i;
		tmpBuf = new byte[buff[tmp]];
		for (int k = 0; k < buff[tmp]; i++, k++)
		{
			tmpBuf[k] = buff[i];
		}
		height = ByteIntConverter.byteArrayToInt(tmpBuf);
		
		tmp = i;
		tmpBuf = new byte[buff[tmp]];
		for (int k = 0; k < buff[tmp]; i++, k++)
		{
			tmpBuf[k] = buff[i];
		}
		xover = ByteIntConverter.byteArrayToInt(tmpBuf);
		
		tmp = i;
		tmpBuf = new byte[buff[tmp]];
		for (int k = 0; k < buff[tmp]; i++, k++)
		{
			tmpBuf[k] = buff[i];
		}
		yover = ByteIntConverter.byteArrayToInt(tmpBuf);
		
		System.out.println(width+"-"+height+"-"+xover+"-"+yover);
		Camera newCam = new Camera(width, height);
		newCam.centerOn(new Location(xover+(width/2), yover+(height/2)));
		
		for (Element e : mapElements)
		{
			if (isInMap(newCam, e.getLocation()))
				els.add(e);
		}
		
		for (int j = 5; j < buff[0]; j+=2)
		{
			if (isInMap(newCam, new Location(buff[j], buff[j+1])))
				els.add(new Unit("Unit", new Location(buff[j], buff[j+1]), 30, 30));
		}
		
		return els;
	}
	private boolean isInMap(Camera cam, Location loc)
	{
		if (loc.x > (cam.getxover() + cam.getWidth()) || loc.x < cam.getxover())
			return false;
		
		if (loc.y > (cam.getyover() + cam.getHeight()) || loc.y < cam.getyover())
			return false;
		
		return true;
	}
	public void setIndex(int setter)
	{
		index = setter;
	}
	public void sendScreenDimensions(int width, int height)
	{
		lck.acquireLock();
		
		this.width = width;
		this.height = height;
		
		cli.writeInt(0);
		cli.writeInt(index);
		cli.writeInt(width);
		cli.writeInt(height);
		
		cli.flush();
		lck.releaseLock();
	}
	private int getIntResponse()
	{
		Integer i;
		
		while ((i = cli.readInt()) == null);
		
		return i;
	}
	
	private boolean requestConnection()
	{
		lck.acquireLock();
		
		cli.writeInt(4);
		//cli.writeString(userName);
		
		cli.flush();
		lck.releaseLock();
		
		setIndex(getIntResponse());
		
		System.out.print("Reading map...");
		
		cli.writeInt(10);
		cli.flush();
		
		while (cli.readInt() != 10);
		
		File fle = new File("tempMap.map");
		cli.readFile(fle);

		World wrld = new World();
		
		try {
			WorldReader.readWorld(wrld, fle.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			return false;
		}
		
		mapElements = wrld.getElements();
		
		System.out.println("done");
		
		return true;
	}
}
