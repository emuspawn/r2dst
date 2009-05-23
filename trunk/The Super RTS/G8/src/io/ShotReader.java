package io;

import java.io.DataInputStream;
import java.io.IOException;

import world.shot.Shot;

public class ShotReader
{
	public static Shot readShot(DataInputStream dis)
	{
		Shot s = null;
		try
		{
			int version = dis.readInt();
			if(version == 1)
			{
				s = readShotV1(dis);
			}
		}
		catch(IOException e){}
		return s;
	}
	private static Shot readShotV1(DataInputStream dis) throws IOException
	{
		int length = dis.readInt();
		String name = "";
		for(int i = length-1; i >= 0; i--)
		{
			name+=dis.readChar();
		}
		double damage = dis.readDouble();
		double movement = dis.readDouble();
		double width = dis.readDouble();
		double height = dis.readDouble();
		double depth = dis.readDouble();
		return new Shot(name, damage, movement, width, height, depth);
	}
}
