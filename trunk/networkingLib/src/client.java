import java.io.*;
import java.net.*;
import java.nio.CharBuffer;


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
			return true;
		} catch (IOException e) {
			if (debug) e.printStackTrace();
			return false;
		}
	}
	
	public String read(int length)
	{
		try {
			CharBuffer buff = CharBuffer.allocate(length);
			reader.read(buff);
			return new String(buff.array());
		} catch (IOException e) {
			if (debug) e.printStackTrace();
		}
		
		return null;
	}
}
