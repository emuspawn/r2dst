import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


public class TCP_Client extends Thread {
	Socket sock;
	ArrayList<String> recvBuffer, sendBuffer;
	BufferedReader reader;
	BufferedWriter writer;
	
	boolean newData;
	
	TCP_Lock lock;
	
	final boolean debug = true;
	
	public TCP_Client(InetAddress addr, int port) throws IOException
	{
		sock = new Socket(addr, port);
		newData = false;
		reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
	
		recvBuffer = new ArrayList<String>();
		sendBuffer = new ArrayList<String>();
		
		lock = new TCP_Lock(false);
		
		start();
	}
	
	public ArrayList<String> getRecvBuffer()
	{
		return new ArrayList<String>(recvBuffer);
	}
	
	public ArrayList<String> getSendBuffer()
	{
		return new ArrayList<String>(sendBuffer);
	}
	
	public void run()
	{
		while (!isInterrupted())
		{
			write();
			read();
		}
	}
	
	public void writeDouble(double i)
	{
		lock.acquire();
		sendBuffer.add(i+"");
		lock.release();
	}
	
	public void writeString(String str)
	{
		lock.acquire();
		sendBuffer.add(str);
		lock.release();
	}
	
	public Double readDouble(boolean block)
	{
		Double ret;
		if (block)
			while((ret = readDouble()) == null);
		else
			ret = readDouble();
		
		return ret;
	}
	
	public Double readDouble()
	{
		Double dbl = null;
		
		lock.acquire();
		for (String data : recvBuffer)
		{		
			try {
				dbl = Double.parseDouble(data);
				recvBuffer.remove(data);
				break;
			} catch (NumberFormatException e) {
			}
		}
		lock.release();

		return dbl;
	}
	
	public String readString(boolean block)
	{
		String ret;
		if (block)
			while ((ret = readString()) == null);
		else
			ret = readString();
		
		return ret;
	}
	
	public String readString()
	{
		String str = null;
		
		lock.acquire();
		for (String data : recvBuffer)
		{
			try {
				Double.parseDouble(data);
			} catch (NumberFormatException e) {
				str = data;
				recvBuffer.remove(data);
				break;
			}
		}
		lock.release();
		
		return str;
	}
	
	//Sends data to the server
	public boolean write()
	{
		String str = "";
		try {
			lock.acquire();
			for (String tmp : sendBuffer)
			{
				str += tmp+"`";
			}
			sendBuffer.removeAll(sendBuffer);
			lock.release();
			
			writer.write(str);
			writer.flush();

			return true;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
			return false;
		}
	}
	
	//Reads a string from the server
	public void read()
	{
		try {
			String data = "";
			char[] chr = new char[1];
			
			while (reader.ready())
			{
				reader.read(chr);
				data = new String(data+chr[0]);
				chr = new char[1];
			}

			Scanner scan = new Scanner(data).useDelimiter("`");
			
			lock.acquire();
			while (scan.hasNext())
			{
				newData = true;
				recvBuffer.add(scan.next());
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
