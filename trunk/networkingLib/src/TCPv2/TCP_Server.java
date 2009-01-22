package TCPv2;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCP_Server implements Runnable {
	ServerSocket sock;
	
	ArrayList<Socket> clients;
	ArrayList<InputStream> inStreams;
	ArrayList<OutputStream> outStreams;
	
	Thread thrd;
	
	public TCP_Server(int port) throws IOException
	{
		sock = new ServerSocket(port);
		clients = new ArrayList<Socket>();
		inStreams = new ArrayList<InputStream>();
		outStreams = new ArrayList<OutputStream>();
		thrd = new Thread(this);
		thrd.start();
	}
	
	public void run()
	{
		while (!thrd.isInterrupted())
		{
			try {
				Socket client = sock.accept();
				
				if (client != null)
				{
					clients.add(client);
					inStreams.add(client.getInputStream());
					outStreams.add(client.getOutputStream());
				}
			} catch (IOException e) {
				if (!thrd.isInterrupted())
					e.printStackTrace();
			}
		}
	}
	
	public boolean close()
	{
		try {
			thrd.interrupt();
			sock.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public SocketAddress getClientSocketAddress(int client)
	{
		return clients.get(client).getRemoteSocketAddress();
	}
	
	public int getClientCount()
	{
		return clients.size();
	}
	
	public int getAvailableByteCount(int client)
	{
		int bytes;
		
		try {
			bytes = inStreams.get(client).available();
		} catch (IOException e) {
			bytes = 0;
		}
		
		return bytes;
	}
	
	public byte[] receive(int client, int length)
	{
		byte[] data = new byte[length];
		
		try {
			if (inStreams.get(client).read(data) != length)
				data = null;
		} catch (IOException e) {
			data = null;
		}
		
		return data;
	}
	
	public boolean send(int client, byte[] data)
	{
		try {
			outStreams.get(client).write(data);
			outStreams.get(client).flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
