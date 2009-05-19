package io;

import java.io.DataOutputStream;
import java.io.IOException;

public class UnitWriter
{
	private static final int version = 2;
	public static void writeUnit(DataOutputStream dos, String name, String weapon, 
			int life, double movement, double energyCost, double metalCost, 
			double energyDrain, double metalDrain, double width, double height, 
			double depth)
	{
		try
		{
			dos.writeInt(version);
			dos.writeInt(name.length());
			dos.writeChars(name);
			dos.writeInt(weapon.length());
			dos.writeChars(weapon);
			dos.writeInt(life);
			dos.writeDouble(energyCost);
			dos.writeDouble(metalCost);
			dos.writeDouble(energyDrain);
			dos.writeDouble(metalDrain);
			dos.writeDouble(movement);
			dos.writeDouble(width);
			dos.writeDouble(height);
			dos.writeDouble(depth);
		}
		catch(IOException e){}
	}
}
