package TCPv2;

import java.net.*;
import java.io.*;

public class TCP_Client extends Thread {
	Socket sock;
	
	ObjectInputStream in;
	ObjectOutputStream out;
	
	public TCP_Client(InetAddress addr, int port) throws IOException
	{
		sock = new Socket(addr, port);
		in = new ObjectInputStream(sock.getInputStream());
		out = new ObjectOutputStream(sock.getOutputStream());
		//We MUST flush here otherwise the TCP_Server will block forever
		out.flush();
		start();
	}
	
	public int getAvailableBytes()
	{
		try {
			return in.available();
		} catch (IOException e) {
			return -1;
		}
	}
	
	public boolean writeObject(Object obj)
	{
		try {
			out.writeObject(obj);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Object readObject() throws ClassNotFoundException
	{
		try {
			return in.readObject();
		} catch (IOException e) {
			return null;
		}
	}
	
	public Short readShort()
	{
		try {
			return in.readShort();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeShort(Short data)
	{
		try {
			out.writeShort(data);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Integer readInt()
	{
		try {
			return in.readInt();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeInt(Integer data)
	{
		try {
			out.writeInt(data);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Byte readByte()
	{
		try {
			return in.readByte();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeByte(Byte data)
	{
		try {
			out.writeByte(data);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Double readDouble()
	{
		try {
			return in.readDouble();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeDouble(Double dbl)
	{
		try {
			out.writeDouble(dbl);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Character readChar()
	{
		try {
			return in.readChar();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeChar(Character chr)
	{
		try {
			out.writeChar(chr);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public String readString()
	{
		try {
			return in.readUTF();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeString(String str)
	{
		try {
			out.writeUTF(str);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Float readFloat()
	{
		try {
			return in.readFloat();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeFloat(Float flt)
	{
		try {
			out.writeFloat(flt);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public Boolean readBool()
	{
		try {
			return in.readBoolean();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean writeBool(Boolean bol)
	{
		try {
			out.writeBoolean(bol);
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

