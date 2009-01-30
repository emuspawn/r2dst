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
	
	public byte[] readBytes()
	{
		try {
			byte[] data = new byte[in.available()];
			
			in.read(data);
			
			return data;
		} catch (IOException e) {
			return null;
		}
	}
	
	public int getAvailableBytes()
	{
		try {
			return in.available();
		} catch (IOException e) {
			return -1;
		}
	}
	
	public Integer readInt()
	{
		int data = 0;
		byte[] bytes = new byte[5];
		
		try {
			in.read(bytes);
		} catch (IOException e) {
			return null;
		}
		
		for (int i = 0; i < bytes.length; i++)
			data += bytes[i];
			
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
				return true;
			}
			
			if (!isNeg)
				while (byteCount <= 5)
				{
					byteCount++;
					if (num > 127) {
						out.write(127);
						num -= 127;
					} else if (num > 0) {
						out.write(num);
					} else {
						out.write(0);
					}
				}
			else
				while (byteCount <= 5)
				{
					byteCount++;
					if (num < -128) {
						out.write(-128);
						num += 128;
					} else if (num < 0) {
						out.write(num);
					} else {
						out.write(0);
					}
				}
			
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Byte readByte()
	{
		try {
			byte[] dat = new byte[1];
			
			in.read(dat);
			
			return dat[0];
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeByte(Byte data)
	{
		try {
			out.write(data);
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

