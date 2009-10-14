package network;

import java.net.*;
import java.io.*;

/**
 * represents a forwarding server, accepts connections and forms server helpers, server helpers
 * relay data they receive from their client to all other server helpers which in turn
 * relay the information to their represctive clients
 * 
 * NOT GOOD FOR GAMES
 * @author Jack
 *
 */
public final class ForwardingServer implements Runnable
{
	private ServerSocket ss;
	private ServerHelper[] sh = new ServerHelper[5];
	
	public ForwardingServer(int port)
	{
		System.out.println("starting tcp server...");
		try
		{
			ss = new ServerSocket(port);
			System.out.println("bound to port "+port);
		}
		catch(IOException e){}
		
		new Thread(this).start();
	}
	public void run()
	{
		System.out.println("accepting connectings...");
		for(;;)
		{
			try
			{
				Socket socket = ss.accept();
				socket.setTcpNoDelay(true);
				System.out.println("tcp socket accepted");
				
				boolean created = false;
				for(byte i = 0; i < sh.length && !created; i++)
				{
					if(sh[i] == null)
					{
						created = true;
						ServerHelper serverHelper = new ServerHelper(socket, sh, i);
						sh[i] = serverHelper;
						System.out.println("server helper created, id = "+i);
					}
				}
			}
			catch(IOException e){}
		}
	}
}
/**
 * helps the server, acts like a relay station, data in gets relayed to all
 * other connected clients, the server helper terminates itself once the client
 * disconnects, each server helper is linked to one client which it constantly
 * receives from and writes to (at the request of other server helpers)
 * @author Jack
 *
 */
class ServerHelper implements Runnable
{
	Socket s;
	byte id; //the index this helper holds in the server helper array
	ServerHelper[] sh;
	
	public ServerHelper(Socket s, ServerHelper[] sh, byte id)
	{
		this.s = s;
		this.id = id;
		this.sh = sh;
		
		try
		{
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeByte(id);
		}
		catch(IOException e){}
		
		new Thread(this).start();
	}
	public synchronized void write(byte[] b, byte id) throws IOException
	{
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		dos.writeByte(b.length);
		dos.writeByte(id);
		dos.write(b);
	}
	public void run()
	{
		DataInputStream dis = null;
		try
		{
			dis = new DataInputStream(s.getInputStream());
		}
		catch(IOException e){}
		
		try
		{
			for(;;)
			{
				//receives and relays the packet to all other clients
				byte length = dis.readByte();
				byte[] b = new byte[length];
				dis.read(b, 0, length);
				for(int i = 0; i < sh.length; i++)
				{
					if(sh[i] != null && i != id)
					{
						sh[i].write(b, id);
					}
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("client "+id+" disconnected");
		}
		sh[id] = null;
		System.out.println("server helper "+id+" terminated");
	}
}