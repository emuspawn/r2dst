package io;

import java.io.DataOutputStream;
import java.io.IOException;

public class UnitWriter
{
	public static void writeUnit(DataOutputStream dos, String name, int life, int movement, int width, int height)
	{
		try
		{
			dos.writeInt(name.length());
			dos.writeChars(name);
			dos.writeInt(life);
			dos.writeInt(movement);
			dos.writeInt(width);
			dos.writeInt(height);
		}
		catch(IOException e){}
	}
}
