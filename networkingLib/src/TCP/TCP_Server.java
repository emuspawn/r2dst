package TCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCP_Server implements Runnable {
	private ServerSocket sock;
	
	private ArrayList<Socket> clients;
	private ArrayList<BufferedInputStream> ins;
	private ArrayList<BufferedOutputStream> outs;
	
	private TCP_Server_Callbacks callbacks;
	
	public TCP_Server(int port, TCP_Server_Callbacks calls) throws IOException
	{
		sock = new ServerSocket(port);
		clients = new ArrayList<Socket>();
		ins = new ArrayList<BufferedInputStream>();
		outs = new ArrayList<BufferedOutputStream>();
		callbacks = calls;

		new AcceptThread(ins, outs, clients, sock, callbacks);
		new Thread(this).start();
	}
	
	public ServerSocket getServerSocket()
	{
		return sock;
	}
	
	//Returned list is read-only unless in ReceiveException handler
	public ArrayList<BufferedInputStream> getInStreamList()
	{
		return ins;
	}
	
	//Returned list is read-only unless in ReceiveException handler
	public ArrayList<BufferedOutputStream> getOutStreamList()
	{
		return outs;
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
				BufferedInputStream in = ins.get(i);
				byte[] buffer;
				try {
					buffer = new byte[in.available()];
					in.read(buffer);
					
					callbacks.DataReceived(i, buffer);
				} catch (IOException e) {
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
	private ArrayList<BufferedInputStream> ins;
	private ArrayList<BufferedOutputStream> outs;
	
	private TCP_Server_Callbacks callbacks;
	
	public AcceptThread(ArrayList<BufferedInputStream> inList,
						ArrayList<BufferedOutputStream> outList,
						ArrayList<Socket> clientList,
						ServerSocket servSock,
						TCP_Server_Callbacks calls)
	{
		ins = inList;
		outs = outList;
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
					BufferedInputStream in = new BufferedInputStream(client.getInputStream());
					BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
					ins.add(in);
					outs.add(out);
					clients.add(client);
					
					callbacks.ClientConnected(client, in, out);
				}
			} catch (IOException e) { 
				callbacks.ConnectException(e);
				return;
			}
		}
	}
}
