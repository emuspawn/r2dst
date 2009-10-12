package network.tcp;

import java.net.*;
import java.io.*;
import network.Server;

public final class TCPServer implements Server, Runnable
{
	private ServerSocket ss;
	TCPProtocol tcpp;
	boolean closeServer = false;
	int threadSpeed;
	
	public TCPServer(int port, TCPProtocol tcpp, int threadSpeed)
	{
		this.tcpp = tcpp;
		this.threadSpeed = threadSpeed;
		System.out.println("starting tcp server...");
		try
		{
			ss = new ServerSocket(port);
			System.out.println("bound to port "+port);
		}
		catch(IOException e){}
		System.out.println("done");
		
		new Thread(this).start();
	}
	public void run()
	{
		while(!closeServer)
		{
			try
			{
				Socket socket = ss.accept();
				socket.setTcpNoDelay(true);
				new TCPServerThread(socket, tcpp, threadSpeed);
				System.out.println("tcp socket accepted");
			}
			catch(IOException e){}
		}
		try
		{
			ss.close();
		}
		catch(IOException e){}
		System.out.println("server closed");
	}
	public void closeServer()
	{
		closeServer = true;
	}
}
