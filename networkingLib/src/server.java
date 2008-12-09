import java.util.ArrayList;
import java.net.*;
import java.io.*;

// TODO: Implement locking for the lists
public class server extends Thread {
	ServerSocket serv;
	ArrayList<Socket> connections;
	ArrayList<BufferedWriter> streamWr;
	ArrayList<BufferedReader> streamRd;
	
	final boolean debug = true;
	
	public server(int listenPort) throws IOException
	{
		serv = new ServerSocket(listenPort);
		
		connections = new ArrayList<Socket>();
		streamWr = new ArrayList<BufferedWriter>();
		streamRd = new ArrayList<BufferedReader>();
		
		start();
	}
	
	public boolean writeFirst(String str)
	{
		return write(0, str);
	}
	
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
	
	public String readFirst()
	{
		return read(0);
	}
	
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
	
	public void run()
	{
		DbgPrint("Listening...");
		while (!isInterrupted())
		{
			Socket newConn;
			try {
				newConn = serv.accept();
			
				if (newConn != null)
				{
					DbgPrint("Got a connection from: "+newConn.getInetAddress());
					connections.add(newConn);
					streamWr.add(new BufferedWriter(new OutputStreamWriter(newConn.getOutputStream())));
					streamRd.add(new BufferedReader(new InputStreamReader(newConn.getInputStream())));
				}
			} catch (IOException e) {}
		}
		DbgPrint("Connecting thread interrupted!");
	}
	
	public void close()
	{
		interrupt();
		
		while (this.isAlive());
		
		try {
			for (BufferedWriter bw : streamWr)
				bw.close();
		
			for (BufferedReader br : streamRd)
				br.close();
		
			for (Socket sc : connections)
				sc.close();
		} catch (SocketException e) {} catch (IOException e) {}
	}
	
	private void DbgPrint(String str)
	{
		if (debug)
			System.out.println(str);
	}
}
