package TCPv3;

import java.net.*;
import java.io.*;

public class TCP_Client extends Thread {
	private Socket sock;
	
	private BufferedInputStream in;
	private BufferedOutputStream out;
	
	public TCP_Client(InetAddress addr, int port) throws IOException
	{
		sock = new Socket(addr, port);
		in = new BufferedInputStream(sock.getInputStream());
		out = new BufferedOutputStream(sock.getOutputStream());
		start();
	}
	
	public Integer readInt()
	{
		int data = 0;
		
		byte nextByte = 0;
		byte[] tempBuffer;
		while (nextByte != -128)
		{
			data += nextByte;
			tempBuffer = new byte[1];
			try {
				in.read(tempBuffer);
				nextByte = tempBuffer[0];
			} catch (IOException e) { return null; }
		}
			
		return data;
	}
	
	public boolean writeInt(Integer data)
	{
		try {
			int byteCount = 0;
			boolean isNeg = data < 0;
			int num = data;
			
			if (data == 0)
			{
				out.write(0);
				out.write(-128);
				return true;
			}
			
			if (!isNeg)
				while (num > 0)
				{
					byteCount++;
					if (num > 127) {
						out.write(127);
						num -= 127;
					} else if (num > 0) {
						out.write(num);
						num = 0;
					}
				}
			else
				while (num < 5)
				{
					byteCount++;
					if (num < -127) {
						out.write(-127);
						num += 127;
					} else if (num < 0) {
						out.write(num);
						num = 0;
					}
				}
			
			out.write(-128);
			
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean flush()
	{
		try {
			out.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
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
}

