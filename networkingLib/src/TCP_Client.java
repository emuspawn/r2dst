import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


public class TCP_Client {
	private Socket sock;
	private ArrayList<String> recvBuffer, sendBuffer;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private final boolean debug = true;
	
	public TCP_Client(InetAddress addr, int port) throws IOException
	{
		sock = new Socket(addr, port);
		reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
	
		recvBuffer = new ArrayList<String>();
		sendBuffer = new ArrayList<String>();
	}
	
	public void clearRecvBuffer()
	{
		recvBuffer.clear();
	}
	
	public void clearSendBuffer()
	{
		sendBuffer.clear();
	}
	
	public ArrayList<String> getRecvBuffer()
	{
		return new ArrayList<String>(recvBuffer);
	}
	
	public ArrayList<String> getSendBuffer()
	{
		return new ArrayList<String>(sendBuffer);
	}
	
	public boolean writeDouble(double i)
	{
		if (!sock.isConnected())
			return false;
		
		sendBuffer.add(i+"");
		
		return true;
	}
	
	public boolean writeString(String str)
	{
		if (!sock.isConnected())
			return false;
		
		sendBuffer.add(str);

		return true;
	}
	
	public Double readDouble()
	{
		Double dbl = null;
		
		if (!sock.isConnected())
			return null;
		
		for (String data : recvBuffer)
		{		
			try {
				dbl = Double.parseDouble(data);
				recvBuffer.remove(data);
				break;
			} catch (NumberFormatException e) {
			}
		}

		return dbl;
	}
	
	public String readString()
	{
		String str = null;
		
		if (!sock.isConnected())
			return null;
		
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
		
		return str;
	}
	
	//Sends data to the server
	public boolean write()
	{
		String str = "";
		
		if (!sock.isConnected())
			return false;
		
		try {
			for (String tmp : sendBuffer)
			{
				str += tmp+"`";
			}
			sendBuffer.removeAll(sendBuffer);
			
			if (str == "")
				return false;
			
			writer.write(str);
			writer.flush();

			return true;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
			return false;
		}
	}
	
	//Reads a string from the server
	public boolean read()
	{
		if (!sock.isConnected())
			return false;
		
		try {
			boolean ret = false;
			String data = "";
			char[] chr = new char[1];
			
			while (reader.ready())
			{
				reader.read(chr);
				data = new String(data+chr[0]);
				chr = new char[1];
			}

			Scanner scan = new Scanner(data).useDelimiter("`");
			
			while (scan.hasNext())
			{
				ret = true;
				recvBuffer.add(scan.next());
			}
			
			return ret;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
			return false;
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
