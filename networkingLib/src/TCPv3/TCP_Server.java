package TCPv3;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCP_Server implements Runnable {
	private ServerSocket sock;
	
	private ArrayList<Socket> clients;
	private ArrayList<BufferedInputStream> ins;
	private ArrayList<BufferedOutputStream> outs;
	
	public TCP_Server(int port) throws IOException
	{
		sock = new ServerSocket(port);
		clients = new ArrayList<Socket>();
		ins = new ArrayList<BufferedInputStream>();
		outs = new ArrayList<BufferedOutputStream>();
		new Thread(this).start();
	}
	
	public void run()
	{
		for (;;)
		{
			Socket client;
			try {
				client = sock.accept();
				
				if (client != null)
				{
					ins.add(new BufferedInputStream(client.getInputStream()));
					outs.add(new BufferedOutputStream(client.getOutputStream()));
					clients.add(client);
				}
			} catch (IOException e) { break; }
		}
	}
	
	public byte[] readBytes(int client)
	{
		try {
			byte[] data = new byte[ins.get(client).available()];
			
			ins.get(client).read(data);
			
			return data;
		} catch (IOException e) {
			return null;
		}
	}
	
	public int getClientCount()
	{
		return clients.size();
	}
	
	public int getAvailableBytes(int client)
	{
		try {
			return ins.get(client).available();
		} catch (IOException e) {
			return -1;
		}
	}
	
	public Integer readInt(int client)
	{
		int data = 0;
		byte[] bytes = new byte[5];
		
		try {
			ins.get(client).read(bytes);
		} catch (IOException e) {
			return null;
		}
		
		for (int i = 0; i < bytes.length; i++)
		{
			data += bytes[i];
			System.out.println("adding "+bytes[i]);
		}
			
		return data;
	}
	
	public boolean writeInt(int client, Integer data)
	{
		try {
			int byteCount = 0;
			boolean isNeg = data < 0;
			int num = data;
			
			if (data == 0)
			{
				outs.get(client).write(0);
				return true;
			}
			
			if (!isNeg)
				while (byteCount <= 5)
				{
					byteCount++;
					if (num > 127) {
						outs.get(client).write(127);
						num -= 127;
					} else if (num > 0) {
						outs.get(client).write(num);
					} else {
						outs.get(client).write(0);
					}
				}
			else
				while (byteCount <= 5)
				{
					byteCount++;
					if (num < -128) {
						outs.get(client).write(-128);
						num += 128;
					} else if (num < 0) {
						outs.get(client).write(num);
					} else {
						outs.get(client).write(0);
					}
				}
			
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Byte readByte(int client)
	{
		try {
			byte[] dat = new byte[1];
			
			ins.get(client).read(dat);
			
			return dat[0];
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeByte(int client, Byte data)
	{
		try {
			outs.get(client).write(data);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean flush(int client)
	{
		try {
			outs.get(client).flush();
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
