package TCPv2;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCP_Server implements Runnable {
	ServerSocket sock;
	
	ArrayList<Socket> clients;
	ArrayList<ObjectInputStream> inStreams;
	ArrayList<ObjectOutputStream> outStreams;
	
	Thread thrd;
	
	public TCP_Server(int port) throws IOException
	{
		sock = new ServerSocket(port);
		clients = new ArrayList<Socket>();
		inStreams = new ArrayList<ObjectInputStream>();
		outStreams = new ArrayList<ObjectOutputStream>();
		thrd = new Thread(this);
		thrd.start();
	}
	
	public void run()
	{
		while (!thrd.isInterrupted())
		{
			try {
				Socket client = sock.accept();
				
				if (client != null)
				{
					clients.add(client);
					ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
					//We MUST flush here otherwise our clients will block forever in the TCP_Client contructor
					out.flush();
					outStreams.add(out);
					inStreams.add(new ObjectInputStream(client.getInputStream()));
				}
			} catch (IOException e) {
				if (!thrd.isInterrupted())
					e.printStackTrace();
			}
		}
	}
	
	public boolean close()
	{
		try {
			thrd.interrupt();
			sock.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public SocketAddress getClientSocketAddress(int client)
	{
		return clients.get(client).getRemoteSocketAddress();
	}
	
	public int getClientCount()
	{
		return clients.size();
	}
	
	public int getAvailableBytes(int client)
	{
		try {
			return inStreams.get(client).available();
		} catch (IOException e) {
			return -1;
		}
	}
	
	public boolean writeObject(int client, Object obj)
	{
		try {
			outStreams.get(client).writeObject(obj);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Object readObject(int client) throws ClassNotFoundException
	{
		try {
			return inStreams.get(client).readObject();
		} catch (IOException e) {
			return null;
		}
	}
	
	public Short readShort(int client)
	{
		try {
			return inStreams.get(client).readShort();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeShort(int client, Short data)
	{
		try {
			outStreams.get(client).writeShort(data);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Integer readInt(int client)
	{
		try {
			return inStreams.get(client).readInt();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeInt(int client, Integer data)
	{
		try {
			outStreams.get(client).writeInt(data);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Byte readByte(int client)
	{
		try {
			return inStreams.get(client).readByte();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeByte(int client, Byte data)
	{
		try {
			outStreams.get(client).writeByte(data);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Double readDouble(int client)
	{
		try {
			return inStreams.get(client).readDouble();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeDouble(int client, Double dbl)
	{
		try {
			outStreams.get(client).writeDouble(dbl);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Character readChar(int client)
	{
		try {
			return inStreams.get(client).readChar();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeChar(int client, Character chr)
	{
		try {
			outStreams.get(client).writeChar(chr);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public String readString(int client)
	{
		try {
			return inStreams.get(client).readUTF();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeString(int client, String str)
	{
		try {
			outStreams.get(client).writeUTF(str);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Float readFloat(int client)
	{
		try {
			return inStreams.get(client).readFloat();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeFloat(int client, Float flt)
	{
		try {
			outStreams.get(client).writeFloat(flt);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Boolean readBool(int client)
	{
		try {
			return inStreams.get(client).readBoolean();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeBool(int client, Boolean bol)
	{
		try {
			outStreams.get(client).writeBoolean(bol);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean flush(int client)
	{
		try {
			outStreams.get(client).flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
