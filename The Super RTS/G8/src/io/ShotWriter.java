package io;

import java.io.DataOutputStream;
import java.io.IOException;

import world.shot.Shot;

public class ShotWriter
{
	private static final int version = 1;
	public static void writeShot(DataOutputStream dos, String name, double damage, 
			double movement, double width, double height, double depth)
	{
		try
		{
			dos.writeInt(version);
			dos.writeInt(name.length());
			dos.writeChars(name);
			dos.writeDouble(damage);
			dos.writeDouble(movement);
			
			dos.writeDouble(width);
			dos.writeDouble(height);
			dos.writeDouble(depth);
		}
		catch(IOException e){}
	}
	public static void writeShot(DataOutputStream dos, Shot s)
	{
		writeShot(dos, s.getName(), s.getDamage(), s.getMovement(), s.getWidth(), s.getHeight(), s.getDepth());
	}
}
