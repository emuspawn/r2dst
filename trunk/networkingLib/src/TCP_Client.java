import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


public class TCP_Client {
	Socket sock;
	BufferedReader reader;
	BufferedWriter writer;
	
	final boolean debug = true;
	
	public TCP_Client(InetAddress addr, int port) throws IOException
	{
		sock = new Socket(addr, port);
		
		reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
	}
	
	public boolean sendIntArray(int[] intArray)
	{
		String ret = "";
		for (int i = 0; i < intArray.length; i++)
		{
			ret += intArray[i]+",";
		}
		
		return write(ret);
	}
	
	public int[] readIntArray()
	{
		ArrayList<Integer> intList = new ArrayList<Integer>();
		String data = read();
		
		if (data == null)
			return null;
		
		Scanner scan = new Scanner(data).useDelimiter(",");
		
		while (scan.hasNextInt())
		{
			intList.add(scan.nextInt());
		}
		
		int[] intArray = new int[intList.size()];
		
		for (int i = 0; i < intArray.length; i++)
		{
			intArray[i] = intList.get(i);
		}
		
		return intArray;
	}
	
	//Sends a string to the server
	public boolean write(String str)
	{
		try {
			writer.write(str);
			writer.flush();
			return true;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
			return false;
		}
	}
	
	//Reads a string from the server
	public String read()
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

			return data;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
		}
		
		return null;
	}
	
	//Closes the socket and streams
	public void close()
	{
		try {
			reader.close();
			writer.close();
			sock.close();
		} catch (IOException e) {}
	}
}
