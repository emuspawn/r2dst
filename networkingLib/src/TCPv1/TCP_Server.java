package TCPv1;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;
import java.io.*;


public class TCP_Server {
	private ServerSocket sock;
	private ArrayList<Socket> connections;
	private ArrayList<ArrayList<String>> recvBuffers, sendBuffers;
	private ArrayList<BufferedWriter> writers;
	private ArrayList<BufferedReader> readers;
	private AcceptThread accThread;
	
	//private TCP_Lock lock;
	
	final boolean debug = true;
	
	public TCP_Server(int port) throws IOException
	{
		sock = new ServerSocket(port);
		connections = new ArrayList<Socket>();
		recvBuffers = new ArrayList<ArrayList<String>>();
		sendBuffers = new ArrayList<ArrayList<String>>();
		writers = new ArrayList<BufferedWriter>();
		readers = new ArrayList<BufferedReader>();
		
		accThread = new AcceptThread(sock, connections, sendBuffers, recvBuffers, writers, readers);
	}
	
	public SocketAddress getClientSocketAddress(int client)
	{
		return connections.get(client).getRemoteSocketAddress();
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
	
	public boolean writeObject(int client, Sendable snd)
	{
		if (client < 0 || client >= connections.size() || !connections.get(client).isConnected() || connections.get(client).isClosed())
			return false;
		
		if (writeString(client, "{OBJ-ST}", true))
		{
			for (String str : snd.toSendStringList())
				if (!writeString(client, str))
					return false;
			
			if (writeString(client, "{OBJ-EN}", true))
				return true;
		}
		
		return false;
	}
	
	public ArrayList<String> readObjectStringList(int client)
	{
		boolean isObject = false;
		ArrayList<String> toRemove = new ArrayList<String>(), data = new ArrayList<String>();
		
		if (client < 0 || client >= connections.size() || !connections.get(client).isConnected() || connections.get(client).isClosed())
			return null;
		
		for (String str : recvBuffers.get(client))
		{
			if (str.equals("{OBJ-EN}"))
			{
				toRemove.add(str);
				break;
			}
			
			if (isObject)
			{
				data.add(str);
				toRemove.add(str);
			}
			
			if (str.equals("{OBJ-ST}"))
			{
				toRemove.add(str);
				isObject = true;
			}
		}
		
		for (String str : toRemove)
			recvBuffers.get(client).remove(str);
		
		if (data.isEmpty())
			return null;
		
		return data;
	}
	
	public boolean clearSendBuffer(int client)
	{
		if (client < 0 || client >= connections.size() || !connections.get(client).isConnected() || connections.get(client).isClosed())
			return false;
		
		sendBuffers.get(client).clear();
		
		return true;
	}
	
	public boolean clearRecvBuffer(int client)
	{
		if (client < 0 || client >= connections.size() || !connections.get(client).isConnected() || connections.get(client).isClosed())
			return false;
		
		recvBuffers.get(client).clear();
		
		return true;
	}
	
	public boolean writeDouble(int client, double i)
	{
		if (client < 0 || client >= connections.size() || !connections.get(client).isConnected() || connections.get(client).isClosed())
			return false;
		
		sendBuffers.get(client).add(i+"");
		
		return true;
	}
	
	public boolean writeString(int client, String str)
	{
		return writeString(client, str, false);
	}
	
	private boolean writeString(int client, String str, boolean internal)
	{
		
		if (client < 0 || client >= connections.size() || !connections.get(client).isConnected() || connections.get(client).isClosed())
			return false;
		
		//The string must not contain reserved characters/strings
		if (!internal && (str.contains("{OBJ-EN}") ||
			str.contains("{OBJ-ST}") ||
			str.contains("`")))
			return false;
		
		sendBuffers.get(client).add(str);
		
		return true;
	}
	
	public Double readDouble(int client)
	{
		Double dbl = null;
		boolean insideObjStr = false;
		
		if (client < 0 || client >= connections.size() || !connections.get(client).isConnected() || connections.get(client).isClosed())
			return null;
		
		for (String data : recvBuffers.get(client))
		{		
			try {
				if (data.equals("{OBJ-ST}"))
					insideObjStr = true;
				else if (data.equals("{OBJ-EN}"))
				{
					insideObjStr = false;
					continue;
				}
				
				if (!insideObjStr)
				{
					dbl = Double.parseDouble(data);
					recvBuffers.get(client).remove(data);
					break;
				}
			} catch (NumberFormatException e) {
			}
		}

		return dbl;
	}
	
	public String readString(int client)
	{
		String str = null;
		boolean insideObjStr = false;
		
		if (client < 0 || client >= connections.size() || !connections.get(client).isConnected() || connections.get(client).isClosed())
			return null;
		
		for (String data : recvBuffers.get(client))
		{
			try {
				Double.parseDouble(data);
			} catch (NumberFormatException e) {
				
				if (data.equals("{OBJ-ST}"))
					insideObjStr = true;
				else if (data.equals("{OBJ-EN}"))
				{
					insideObjStr = false;
					continue;
				}
				
				if (!insideObjStr)
				{
					str = data;
					recvBuffers.get(client).remove(data);
					break;
				}
			}
		}
		
		return str;
	}
	
	//Sends data to the server
	public boolean write(int client)
	{
		String str = "";
		
		if (client < 0 || client >= connections.size() || !connections.get(client).isConnected() || connections.get(client).isClosed())
			return false;
		
		try {
			for (String tmp : sendBuffers.get(client))
			{
				str += tmp+"`";
			}
			
			sendBuffers.get(client).clear();
			
			if (str == "")
				return false;

			writers.get(client).write(str);
			writers.get(client).flush();
			
			return true;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
			return false;
		}
	}
	
	//Reads a string from the server
	public boolean read(int client)
	{
		if (client < 0 || client >= connections.size() || !connections.get(client).isConnected() || connections.get(client).isClosed())
			return false;
		
		try {
			boolean ret = false;
			String data = "";
			char[] chr = new char[1];
			
			while (readers.get(client).ready())
			{
				readers.get(client).read(chr);
				data = new String(data+chr[0]);
				chr = new char[1];
			}

			Scanner scan = new Scanner(data).useDelimiter("`");
			
			while (scan.hasNext())
			{
				ret = true;
				recvBuffers.get(client).add(scan.next());
			}
			
			return ret;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
			return false;
		}
	}
	
	//Closes the socket and streams
	public boolean close()
	{
		if (sock.isClosed())
			return false;
		
		try {
			accThread.interrupt();
			sock.close();
			return true;
		} catch (IOException e) {
			return false;
		}
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
	
	public AcceptThread(ServerSocket servSock, ArrayList<Socket> connects, ArrayList<ArrayList<String>> send, ArrayList<ArrayList<String>> recv,
						ArrayList<BufferedWriter> bw, ArrayList<BufferedReader> br)
	{
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
				writers.add(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));
				readers.add(new BufferedReader(new InputStreamReader(sock.getInputStream())));
				recvBuf.add(new ArrayList<String>());
				sendBuf.add(new ArrayList<String>());
				connections.add(sock);
			} catch (IOException e) {
				if (!isInterrupted())
					e.printStackTrace();
			}
		}
	}
}