package io;

import java.io.DataOutputStream;
import java.io.IOException;

public final class WeaponWriter
{
	private static final int version = 1;
	public static void writeWeapon(DataOutputStream dos, String name, String shot, double range, int reload)
	{
		try
		{
			dos.writeInt(version);
			dos.writeInt(name.length());
			dos.writeChars(name);
			dos.writeInt(shot.length());
			dos.writeChars(shot);
			dos.writeDouble(range);
			dos.writeInt(reload);
		}
		catch(IOException e){}
	}
}
