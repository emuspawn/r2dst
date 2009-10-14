package game;

import java.net.*;
import java.io.*;

/**
 * the game server
 * 
 * 1. waits a specified time, in that time gathers user input
 * 2. sends user inputs and a time step value to the clients
 * 
 * @author Jack
 *
 */
public final class Server implements Runnable
{
	private ServerSocket ss;
	private ServerHelper[] sh = new ServerHelper[2];
	private long waitTime;
	
	int userBufferLength = 5; //the buffer for user inputs
	KeyAction[] ka = new KeyAction[userBufferLength];
	//byte[][] ka = new byte[userBufferLength];
	int kaIndex = 0;
	
	public Server(int port, long waitTime)
	{
		System.out.println("starting tcp server...");
		this.waitTime = waitTime;
		try
		{
			ss = new ServerSocket(port);
			System.out.println("bound to port "+port);
			
			new Thread(this).start();
		}
		catch(IOException e){}
	}
	public void run()
	{
		System.out.println("accepting connectings...");
		for(int a = 0; a < sh.length; a++)
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
						ServerHelper serverHelper = new ServerHelper(this, socket, sh, i);
						sh[i] = serverHelper;
						System.out.println("server helper created, id = "+i);
					}
				}
			}
			catch(IOException e){}
		}
		System.out.println("accepted all connections, starting server helpers");
		for(int i = 0; i < sh.length; i++)
		{
			new Thread(sh[i]).start();
		}
		System.out.println("server helpers running");
		System.out.println("starting main server loop, waits "+waitTime+" (ms) before sending");
		try
		{
			for(;;)
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataOutputStream bdos = new DataOutputStream(baos);
				bdos.writeLong(waitTime);
				bdos.writeInt(kaIndex); //the number of user actions accumulated this time step
				for(int a = 0; a < ka.length && ka[a] != null; a++)
				{
					bdos.write(ka[a].toByteArray());
				}
				
				for(int i = 0; i < sh.length; i++)
				{
					sh[i].write(baos.toByteArray());
				}
				ka = new KeyAction[userBufferLength];
				kaIndex = 0;
				try
				{
					Thread.sleep(0);
				}
				catch(InterruptedException a){}
			}
		}
		catch(IOException e)
		{
			System.out.println("io exception, server exiting");
		}
	}
	/**
	 * updates the server list of user inputs
	 * @param bdis
	 */
	public void updateServer(DataInputStream dis) throws IOException
	{
		if(kaIndex < ka.length)
		{
			kaIndex++;
			byte owner = dis.readByte();
			char c = dis.readChar();
			boolean pressed = dis.readBoolean();
			ka[kaIndex] = new KeyAction(owner, c, pressed);
		}
		//System.out.println("input received: "+ka[kaIndex-1]);
		/*byte length = dis.readByte();
		byte[] b = new byte[length];
		dis.read(b, 0, length);*/
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
	Server server;
	
	public ServerHelper(Server server, Socket s, ServerHelper[] sh, byte id)
	{
		this.s = s;
		this.id = id;
		this.sh = sh;
		this.server = server;
		
		try
		{
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeByte(id);
		}
		catch(IOException e){}
		
		//new Thread(this).start();
	}
	public synchronized void write(byte[] b) throws IOException
	{
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		dos.writeByte(b.length);
		//dos.writeByte(id);
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
				/*byte length = dis.readByte();
				byte[] b = new byte[length];
				dis.read(b, 0, length);*/
				
				/*for(int i = 0; i < sh.length; i++)
				{
					if(sh[i] != null && i != id)
					{
						sh[i].write(b, id);
					}
				}*/
				
				server.updateServer(dis);
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