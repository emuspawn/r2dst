package TCPv1;
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
	
	public boolean writeObject(Sendable snd)
	{
		if (writeString("{OBJ-ST}", true))
		{
			for (String str : snd.toSendStringList())
				if (!writeString(str))
					return false;
			
			if (writeString("{OBJ-EN}", true))
				return true;
		}
		
		return false;
	}
	
	public ArrayList<String> readObjectStringList()
	{
		boolean isObject = false;
		ArrayList<String> toRemove = new ArrayList<String>(), data = new ArrayList<String>();
		
		if (!sock.isConnected() || sock.isClosed())
			return null;
		
		for (String str : recvBuffer)
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
			recvBuffer.remove(str);
		
		if (data.isEmpty())
			return null;
		
		return data;
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
		if (!sock.isConnected() || sock.isClosed())
			return false;
		
		sendBuffer.add(i+"");
		
		return true;
	}
	
	public boolean writeString(String str)
	{
		return writeString(str, false);
	}
	
	private boolean writeString(String str, boolean internal)
	{
		if (!sock.isConnected() || sock.isClosed())
			return false;
		
		//The string must not contain reserved characters/strings
		if (!internal && (str.contains("{OBJ-EN}") ||
			str.contains("{OBJ-ST}") ||
			str.contains("`")))
			return false;
		
		sendBuffer.add(str);

		return true;
	}
	
	public Double readDouble()
	{
		Double dbl = null;
		boolean insideObjStr = false;
		
		if (!sock.isConnected() || sock.isClosed())
			return null;
		
		for (String data : recvBuffer)
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
					recvBuffer.remove(data);
					break;
				}
			} catch (NumberFormatException e) {
			}
		}

		return dbl;
	}
	
	public String readString()
	{
		String str = null;
		boolean insideObjStr = false;
		
		if (!sock.isConnected() || sock.isClosed())
			return null;
		
		for (String data : recvBuffer)
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
					recvBuffer.remove(data);
					break;
				}
			}
		}
		
		return str;
	}
	
	//Sends data to the server
	public boolean write()
	{
		String str = "";
		
		if (!sock.isConnected() || sock.isClosed())
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
		if (!sock.isConnected() || sock.isClosed())
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
	public boolean close()
	{
		if (sock.isClosed())
			return false;
		
		try {
			sock.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
