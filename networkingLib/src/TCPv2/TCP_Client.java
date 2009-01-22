package TCPv2;

import java.net.*;
import java.io.*;

public class TCP_Client extends Thread {
	Socket sock;
	InputStream in;
	OutputStream out;
	
	public TCP_Client(InetAddress addr, int port) throws IOException
	{
		sock = new Socket(addr, port);
		in = sock.getInputStream();
		out = sock.getOutputStream();
		start();
	}
	
	public byte[] receive(int length)
	{
		byte[] data = new byte[length];
		
		try {
			if (in.read(data) != length)
				data = null;
		} catch (IOException e) {
			data = null;
		}
		
		return data;
	}
	
	public int getAvailableByteCount()
	{
		int bytes;
		
		try {
			bytes = in.available();
		} catch (IOException e) {
			bytes = 0;
		}
		
		return bytes;
	}
	
	public boolean close()
	{
		try {
			sock.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean send(byte[] data)
	{
		try {
			out.write(data);
			out.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}

