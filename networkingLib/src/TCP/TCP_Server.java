package TCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCP_Server implements Runnable {
	private ServerSocket sock;
	
	private ArrayList<Socket> clients;
	
	private TCP_Server_Callbacks callbacks;
	
	public TCP_Server(int port, TCP_Server_Callbacks calls) throws IOException
	{
		sock = new ServerSocket(port);
		clients = new ArrayList<Socket>();
		callbacks = calls;

		new AcceptThread(clients, sock, callbacks);
		new Thread(this).start();
	}
	
	public ServerSocket getServerSocket()
	{
		return sock;
	}
	
	//Returned list is read-only unless in ReceiveException handler
	public ArrayList<Socket> getClientList()
	{
		return clients;
	}
	
	public void run()
	{
		for (;;)
		{
			for (int i = 0; i < clients.size(); i++)
			{
				InputStream in;
				byte[] buffer;
				
				try {
					in = clients.get(i).getInputStream();
					
					if (in.available() == 0)
						continue;
					
					buffer = new byte[in.available()];
					in.read(buffer);
					
					callbacks.DataReceived(i, buffer);
				} catch (IOException e) {
					clients.remove(i);
					callbacks.ReceiveException(i, e);
					break;
				}
			}
		}
	}
}

class AcceptThread extends Thread
{
	private ServerSocket sock;
	
	private ArrayList<Socket> clients;
	
	private TCP_Server_Callbacks callbacks;
	
	public AcceptThread(ArrayList<Socket> clientList,
						ServerSocket servSock,
						TCP_Server_Callbacks calls)
	{
		clients = clientList;
		sock = servSock;
		callbacks = calls;

		start();
	}
	
	public void run()
	{
		while (!isInterrupted())
		{
			Socket client;
			try {
				client = sock.accept();
				
				if (client != null)
				{
					clients.add(client);
					
					callbacks.ClientConnected(client);
				}
			} catch (IOException e) { 
				callbacks.ConnectException(e);
				return;
			}
		}
	}
}
