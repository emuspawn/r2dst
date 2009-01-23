package connection;

import java.io.*;
import java.util.ArrayList;

public class KeyMap
{
	ArrayList<UserAction> ua = new ArrayList<UserAction>();
	public KeyMap(DataInputStream dis)
	{
		try
		{
			int length = dis.readInt();
			String s = new String();
			for(int i = length-1; i >= 0; i--)
			{
				s+=dis.readChar();
			}
			//System.out.println(s);
			String[] parts = s.split("\n");
			String[] temp;
			for(int i = parts.length-1; i >= 0; i--)
			{
				temp = parts[i].split("-");
				if(temp.length == 3)
				{
					ua.add(new UserAction(temp[0].charAt(0), temp[1], Byte.parseByte(temp[2])));
				}
			}
			/*for(int i = ua.size()-1; i >= 0; i--)
			{
				System.out.println(ua.get(i));
			}*/
		}
		catch(IOException e){}
	}
	public String getUserAction(byte b)
	{
		for(int i = ua.size()-1; i >= 0; i--)
		{
			if(ua.get(i).getByte() == b)
			{
				return ua.get(i).getUserAction();
			}
		}
		return new String("unrecognized user action");
	}
	public byte getUserActionByte(char c)
	{
		for(int i = ua.size()-1; i >= 0; i--)
		{
			if(ua.get(i).getCharacter() == c)
			{
				return ua.get(i).getByte();
			}
		}
		System.out.println(c+" does not correspond to a recognized user action");
		return -1;
	}
}
