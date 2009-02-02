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
	
	public int getClientCount()
	{
		return clients.size();
	}
	
	public Integer readInt(int client)
	{
		int data = 0;
		
		byte nextByte = 0;
		byte[] tempBuffer;
		while (nextByte != -128)
		{
			data += nextByte;
			tempBuffer = new byte[1];
			try {
				ins.get(client).read(tempBuffer);
				nextByte = tempBuffer[0];
			} catch (IOException e) { return null; }
		}
			
		return data;
	}
	
	public boolean writeInt(int client, Integer data)
	{
		try {
			boolean isNeg = data < 0;
			int num = data;
			
			if (data == 0)
			{
				outs.get(client).write(0);
				outs.get(client).write(-128);
				return true;
			}
			
			if (!isNeg)
				while (num > 0)
				{
					if (num > 127) {
						outs.get(client).write(127);
						num -= 127;
					} else if (num > 0) {
						outs.get(client).write(num);
						num = 0;
					}
				}
			else
				while (num < 0)
				{
					if (num < -127) {
						outs.get(client).write(-127);
						num += 127;
					} else if (num < 0) {
						outs.get(client).write(num);
						num = 0;
					}
				}
			
			outs.get(client).write(-128);
			
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean writeFile(int client, File fle)
	{
		try {
			FileInputStream fIn = new FileInputStream(fle);
			
			while (fIn.available() > 0)
			{
				byte[] dataBuffer = new byte[fIn.available()];
				
				fIn.read(dataBuffer);
				
				outs.get(client).write(dataBuffer);
			}
			
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean readFile(int client, File fle)
	{
		try {
			FileOutputStream fOut = new FileOutputStream(fle);
			
			while (ins.get(client).available() > 0)
			{
				byte[] dataBuffer = new byte[ins.get(client).available()];
				
				ins.get(client).read(dataBuffer);
				
				fOut.write(dataBuffer);
			}
			
			return true;
		} catch (FileNotFoundException e) {
			return false;
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
