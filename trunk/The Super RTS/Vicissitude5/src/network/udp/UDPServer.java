package network.udp;

import java.io.*;
import java.net.*;
import network.Server;

public abstract class UDPServer implements Server, Runnable
{
	private DatagramSocket socket = null;
	UDPServerProtocol udpsp;
	int bufferSize;
	boolean closeServer = false;
	
	public UDPServer(int port, UDPServerProtocol udpsp)
	{
		System.out.println("starting udp server...");
		this.udpsp = udpsp;
		this.bufferSize = udpsp.getBufferSize();
		try
		{
			socket = new DatagramSocket(port);
			System.out.println("bound to port "+port);
		}
		catch(IOException e){}
		System.out.println("done");
		
		new Thread(this).start();
	}
	public void closeServer()
	{
		closeServer = true;
	}
	public void run()
	{
		while(!closeServer);
		{
			try
			{
				byte[] buf = new byte[bufferSize];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				udpsp.interpretPacket(packet, buf);
			}
			catch(IOException e){}
		}
		socket.close();
	}
}
