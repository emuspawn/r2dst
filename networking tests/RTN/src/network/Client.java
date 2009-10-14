package network;

import java.io.*;
import java.net.Socket;

public abstract class Client implements Runnable
{
	private Socket s;
	private DataOutputStream dos;
	private DataInputStream dis;
	
	private byte id; //the id of this engine
	private boolean connected = false;
	
	/**
	 * creates a new client which automatically attempts to connect to the
	 * server specified by the passed arguments upon its creation, if the
	 * client fails to connect it does not start its receiver thread
	 * @param host the host ip or computer name
	 * @param port the port the server is bound to
	 */
	public Client(String host, int port)
	{
		System.out.println("starting client...");
		try
		{
			s = new Socket(host, port);
			System.out.println("connected to "+host+", port = "+port);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			System.out.println("io streams constructed");
			
			id = dis.readByte();
			System.out.println("id = "+id);
			
			System.out.println("client receiver setup complete");
			connected = true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		if(connected)
		{
			new Thread(this).start();
		}
	}
	/**
	 * checks to see if the client is connected
	 * @return returns true if the client is connected to the server,
	 * false otherwise
	 */
	public boolean isConnected()
	{
		return connected;
	}
	public byte getID()
	{
		return id;
	}
	/**
	 * the method that actually writes everything to the output stream, everything
	 * must go through this method so that multithreading doesnt cause data to be
	 * written out of order
	 * @param b
	 */
	public void write(byte[] b)
	{
		if(b.length > 0)
		{
			try
			{
				dos.writeByte(b.length);
				dos.write(b);
			}
			catch(IOException e){}
		}
	}
	/**
	 * continually reads the incoming packets
	 * 
	 * first byte length, second id, the rest is the byte array
	 */
	public void run()
	{
		for(;;)
		{
			try
			{
				byte length = dis.readByte();
				//byte id = dis.readByte();
				byte[] b = new byte[length];
				dis.read(b, 0, length);
				
				ByteArrayInputStream bais = new ByteArrayInputStream(b);
				DataInputStream bdis = new DataInputStream(bais);
				
				interpretByteArray(bdis, id);
			}
			catch(IOException e){}
		}
	}
	/**
	 * called every time the client receives a byte array packet from the
	 * server, the input stream is a byte input stream to the byte array
	 * received
	 * @param bdis
	 * @param id
	 */
	protected abstract void interpretByteArray(DataInputStream bdis, byte id);
}
