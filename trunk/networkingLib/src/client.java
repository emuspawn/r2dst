import java.io.*;
import java.net.*;


public class client {
	Socket sock;
	BufferedReader reader;
	BufferedWriter writer;
	
	final boolean debug = true;
	
	public client(InetAddress addr, int port) throws IOException
	{
		sock = new Socket(addr, port);
		
		reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
	}
	
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
}
