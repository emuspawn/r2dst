import java.util.ArrayList;
import java.net.*;
import java.io.*;

// TODO: Implement locking for the lists
public class TCP_Server {
	private ServerSocket serv;
	private ArrayList<Socket> connections;
	private ArrayList<BufferedWriter> streamWr;
	private ArrayList<BufferedReader> streamRd;
	
	private Thread connAcc, list;
	
	private final boolean debug = true;
	
	public TCP_Server(int listenPort) throws IOException
	{
		serv = new ServerSocket(listenPort);
		
		connections = new ArrayList<Socket>();
		streamWr = new ArrayList<BufferedWriter>();
		streamRd = new ArrayList<BufferedReader>();
		
		//This starts the thread which accepts new connections from clients
		connAcc = new connectionAccepter(serv, streamRd, streamWr, connections, debug);
		
		//This starts the thread that removes disconnected clients
		list = new listCleaner(streamRd, streamWr, connections, debug);
	}
	
	//This will return the number of clients connected
	public int getClientCount()
	{
		return connections.size();
	}
	
	//This will send a string to the first registered client
	public boolean writeFirst(String str)
	{
		return write(0, str);
	}
	
	//This will send a string to all clients registered to this server
	public boolean writeAll(String str)
	{
		int i;
		for (i = 0; i < streamWr.size(); i++)
			write(i, str);
		
		return !streamWr.isEmpty();
	}
	
	//This will send a string to one client
	public boolean write(int client, String str)
	{
		if (client < 0 || client >= streamWr.size())
		    return false;
		
		try {
			streamWr.get(client).write(str);
			streamWr.get(client).flush();
			return true;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
			return false;
		}
	}
	
	//This will read data from the first client
	public String readFirst()
	{
		return read(0);
	}
	
	//This will read data from a specific client
	public String read(int client)
	{
		if (client < 0 || client >= streamRd.size())
			return null;
		
		try {
			String data = "";
			char[] chr = new char[1];
			
			while (streamRd.get(client).ready())
			{
				streamRd.get(client).read(chr);
				data = new String(data+chr[0]);
				chr = new char[1];
			}

			return data;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
		}
		
		return null;
	}
	
	//This kills the connection thread and closes all the sockets and streams
	public void close()
	{
		connAcc.interrupt();
		list.interrupt();
		
		//while (list.isAlive() || connAcc.isAlive());
		
		try {
			for (BufferedWriter bw : streamWr)
				bw.close();
		
			for (BufferedReader br : streamRd)
				br.close();
		
			for (Socket sc : connections)
				sc.close();
		} catch (SocketException e) {} catch (IOException e) {}
	}
}

//Accepts new connections
class connectionAccepter extends Thread
{
	private ArrayList<BufferedReader> readerList;
	private ArrayList<BufferedWriter> writerList;
	private ArrayList<Socket> socketList;
	private ServerSocket servSock;
	
	boolean debug;
	
	public connectionAccepter(ServerSocket serv, ArrayList<BufferedReader> br, ArrayList<BufferedWriter> bw, ArrayList<Socket> sk, boolean dbg)
	{
		servSock = serv;
		readerList = br;
		writerList = bw;
		socketList = sk;
		debug = dbg;
		
		start();
	}
	
	public void run()
	{
		DbgPrint("Connecting thread started");
		while (!isInterrupted())
		{
			Socket newConn;
			try {
				newConn = servSock.accept();
			
				if (newConn != null)
				{
					DbgPrint("Accepted a connection from: "+newConn.getInetAddress().toString().substring(1));
					socketList.add(newConn);
					writerList.add(new BufferedWriter(new OutputStreamWriter(newConn.getOutputStream())));
					readerList.add(new BufferedReader(new InputStreamReader(newConn.getInputStream())));
				}
			} catch (IOException e) {}
		}
		DbgPrint("Connecting thread interrupted!");
	}
	
	//This outputs debug info if debug is true
	private void DbgPrint(String str)
	{
		if (debug)
			System.out.println(str);
	}
}

//Removes closed sockets from the list
class listCleaner extends Thread
{
	ArrayList<BufferedReader> readerList;
	ArrayList<BufferedWriter> writerList;
	ArrayList<Socket> socketList;
	
	boolean debug;
	
	public listCleaner(ArrayList<BufferedReader> br, ArrayList<BufferedWriter> bw, ArrayList<Socket> sk, boolean dbg)
	{
		readerList = br;
		writerList = bw;
		socketList = sk;
		debug = dbg;
		
		start();
	}
	
	public void run()
	{
		DbgPrint("List cleaner thread started");
		while (!isInterrupted())
		{
			ArrayList<Integer> rem = new ArrayList<Integer>();
			for (int i = 0; i < socketList.size(); i++)
			{
				if (!socketList.get(i).isConnected())
					rem.add(i);
			}
			
			for (Integer idx : rem)
			{
				DbgPrint("Removing client number "+idx);
				socketList.remove(idx);
				readerList.remove(idx);
				writerList.remove(idx);
			}
		}
		DbgPrint("List cleaner thread interrupted!");
	}
	
	//This outputs debug info if debug is true
	private void DbgPrint(String str)
	{
		if (debug)
			System.out.println(str);
	}
}