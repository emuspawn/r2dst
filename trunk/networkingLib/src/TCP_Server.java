import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;
import java.io.*;

public class TCP_Server extends Thread {
	ServerSocket sock;
	ArrayList<Socket> connections;
	ArrayList<ArrayList<String>> recvBuffers, sendBuffers;
	ArrayList<BufferedWriter> writers;
	ArrayList<BufferedReader> readers;
	
	TCP_Lock lock;
	
	final boolean debug = true;
	
	public TCP_Server(int port) throws IOException
	{
		lock = new TCP_Lock(false);
		sock = new ServerSocket(port);
		connections = new ArrayList<Socket>();
		recvBuffers = new ArrayList<ArrayList<String>>();
		sendBuffers = new ArrayList<ArrayList<String>>();
		writers = new ArrayList<BufferedWriter>();
		readers = new ArrayList<BufferedReader>();
		
		new AcceptThread(sock, connections, sendBuffers, recvBuffers, writers, readers, lock);
		
		start();
	}
	
	public int getClientCount()
	{
		return connections.size();
	}
	
	public ArrayList<String> getRecvBuffer(int client)
	{
		return new ArrayList<String>(recvBuffers.get(client));
	}
	
	public ArrayList<String> getSendBuffer(int client)
	{
		return new ArrayList<String>(sendBuffers.get(client));
	}
	
	public void run()
	{
		while (!isInterrupted())
		{
			if (!connections.isEmpty())
				for (int i = 0; i < connections.size(); i++)
				{
					write(i);
					read(i);
				}
		}
	}
	
	public void writeDouble(int client, double i)
	{
		lock.acquire();
		sendBuffers.get(client).add(i+"");
		lock.release();
	}
	
	public void writeString(int client, String str)
	{
		lock.acquire();
		sendBuffers.get(client).add(str);
		lock.release();
	}
	
	public Double readDouble(int client, boolean block)
	{
		Double ret;
		if (block)
			while((ret = readDouble(client)) == null);
		else
			ret = readDouble(client);
		
		return ret;
	}
	
	public Double readDouble(int client)
	{
		Double dbl = null;
		
		lock.acquire();
		for (String data : recvBuffers.get(client))
		{		
			try {
				dbl = Double.parseDouble(data);
				recvBuffers.get(client).remove(data);
				break;
			} catch (NumberFormatException e) {
			}
		}
		lock.release();

		return dbl;
	}
	
	public String readString(int client, boolean block)
	{
		String ret;
		if (block)
			while ((ret = readString(client)) == null);
		else
			ret = readString(client);
		
		return ret;
	}
	
	public String readString(int client)
	{
		String str = null;
		
		lock.acquire();
		for (String data : recvBuffers.get(client))
		{
			try {
				Double.parseDouble(data);
			} catch (NumberFormatException e) {
				str = data;
				recvBuffers.get(client).remove(data);
				break;
			}
		}
		lock.release();
		
		return str;
	}
	
	//Sends data to the server
	public boolean write(int client)
	{
		String str = "";
		try {
			lock.acquire();
			for (String tmp : sendBuffers.get(client))
			{
				str += tmp+"`";
			}
			
			sendBuffers.get(client).removeAll(sendBuffers.get(client));
			lock.release();

			writers.get(client).write(str);
			writers.get(client).flush();
			
			return true;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
			return false;
		}
	}
	
	//Reads a string from the server
	public void read(int client)
	{
		try {
			String data = "";
			char[] chr = new char[1];
			
			lock.acquire();
			while (readers.get(client).ready())
			{
				readers.get(client).read(chr);
				data = new String(data+chr[0]);
				chr = new char[1];
			}

			Scanner scan = new Scanner(data).useDelimiter("`");
			
			while (scan.hasNext())
			{
				recvBuffers.get(client).add(scan.next());
			}
			lock.release();
		} catch (IOException e) {
			if (debug) e.printStackTrace();
		}
	}
	
	//Closes the socket and streams
	public void close()
	{
		try {
			sock.close();
		} catch (IOException e) {}
	}
}

class AcceptThread extends Thread
{
	ServerSocket serv;
	ArrayList<Socket> connections;
	ArrayList<ArrayList<String>> recvBuf;
	ArrayList<ArrayList<String>> sendBuf;
	ArrayList<BufferedWriter> writers;
	ArrayList<BufferedReader> readers;
	
	TCP_Lock lock;
	
	public AcceptThread(ServerSocket servSock, ArrayList<Socket> connects, ArrayList<ArrayList<String>> send, ArrayList<ArrayList<String>> recv,
						ArrayList<BufferedWriter> bw, ArrayList<BufferedReader> br, TCP_Lock lck)
	{
		lock = lck;
		sendBuf = send;
		recvBuf = recv;
		connections = connects;
		serv = servSock;
		writers = bw;
		readers = br;
		
		start();
	}
	
	public void run()
	{
		while (!isInterrupted())
		{
			try {
				Socket sock = serv.accept();
				lock.acquire();
				writers.add(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));
				readers.add(new BufferedReader(new InputStreamReader(sock.getInputStream())));
				recvBuf.add(new ArrayList<String>());
				sendBuf.add(new ArrayList<String>());
				connections.add(sock);
				lock.release();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}