package network.tcp;

import java.io.*;
import java.net.Socket;

/**
 * a tcp server thread connected to a socket, periodically reads and interprets
 * IO streams on the socket, closes the socket when thread terminated
 * @author Jack
 *
 */
public final class TCPServerThread implements Runnable
{
	Socket s;
	TCPProtocol tcpp;
	private int threadSpeed;
	boolean terminate = false;
	
	public TCPServerThread(Socket s, TCPProtocol tcpp, int threadSpeed)
	{
		this.s = s;
		this.tcpp = tcpp;
		this.threadSpeed = threadSpeed;
		
		new Thread(this).start();
	}
	/**
	 * terminates the thread
	 */
	public void terminateThread()
	{
		terminate = true;
	}
	public void run()
	{
		while(!terminate)
		{
			try
			{
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				boolean end = tcpp.interpretStream(dis, dos);
				if(end)
				{
					break;
				}
			}
			catch(IOException e)
			{
				System.out.println("io exception in tcp server thread, terminating thread");
				terminateThread();
			}
			try
			{
				Thread.sleep(threadSpeed);
			}
			catch(InterruptedException e){}
		}
		try
		{
			s.close();
			System.out.println("socket closed");
		}
		catch(IOException e){}
	}
}
