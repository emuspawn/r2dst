package network.components;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import network.protocol.*;

public class Client
{
	protected DataOutputStream dos;
	protected DataInputStream dis;
	Socket s;
	boolean justConnected = true;
	int clientNum = 0;
	protected OutputProtocol op = new OutputProtocol();
	protected InputProtocol ip = new InputProtocol();
	
	public Client(String server, int port)
	{
		connect(server, port);
		
		try
		{
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		}
		catch(IOException e){}
		
	}
	public void performClientFunctions()
	{
		newConnectionFunctions();
		receiveData();
		sendData();
	}
	protected void sendData(){}
	protected void receiveData(){}
	private void newConnectionFunctions()
	{
		if(justConnected)
		{
			try
			{
				dos.writeInt(1);
				clientNum = dis.readInt();
				justConnected = false;
			}
			catch(IOException e){}
		}
	}
	private void connect(String server, int port)
	{
		try
		{
			s = new Socket(server, port);
	    }
		catch (UnknownHostException e)
		{
	        System.err.println("UnknownHostException: "+server);
	        System.exit(1);
	    }
		catch (IOException e)
		{
			System.err.println("Couldn't get I/O for the connection to: "+server+" Server.");
			System.exit(1);
	    }
	    //System.out.println("Client Connected to "+server);
	}
}
