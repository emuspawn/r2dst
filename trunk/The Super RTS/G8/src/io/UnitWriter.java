package io;

import java.io.DataOutputStream;
import java.io.IOException;

public final class UnitWriter
{
	private static final int version = 4;
	public static void writeUnit(DataOutputStream dos, String name, String weapon, 
			double life, double movement, double energyCost, double metalCost, 
			double energyDrain, double metalDrain, double energyStorage, double metalStorage, 
			double width, double height, double depth, int buildTime, String[] buildTree)
	{
		try
		{
			dos.writeInt(version);
			dos.writeInt(name.length());
			dos.writeChars(name);
			dos.writeInt(weapon.length());
			dos.writeChars(weapon);
			
			dos.writeDouble(life);
			dos.writeDouble(movement);
			
			dos.writeDouble(energyCost);
			dos.writeDouble(metalCost);
			dos.writeDouble(energyDrain);
			dos.writeDouble(metalDrain);
			dos.writeDouble(energyStorage);
			dos.writeDouble(metalStorage);
			
			dos.writeDouble(width);
			dos.writeDouble(height);
			dos.writeDouble(depth);
			
			dos.writeInt(buildTime);
			
			dos.writeInt(buildTree.length);
			for(int i = 0; i < buildTree.length; i++)
			{
				dos.writeInt(buildTree[i].length());
				dos.writeChars(buildTree[i]);
			}
		}
		catch(IOException e){}
	}
}
