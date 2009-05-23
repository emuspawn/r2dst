package io;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import sgEngine.EngineConstants;
import world.shot.weapon.Weapon;
import world.unit.Unit;

public class UnitReader
{
	/**
	 * reads unit data and creates a unit
	 * @param dis the input stream for the unit file
	 * @param location the location the unit is to be created at
	 * @return returns a unit read from the passed unit file stream,
	 * null if stream is null or io exception during reading
	 */
	public static Unit readUnit(DataInputStream dis)
	{
		Unit u = null;
		try
		{
			int version = dis.readInt();
			if(version == 2)
			{
				u = readUnitV2(dis);
			}
			else if(version == 3)
			{
				u = readUnitV3(dis);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("IO exception, returning null");
		}
		return u;
	}
	private static Unit readUnitV3(DataInputStream dis) throws IOException
	{
		int length = dis.readInt();
		String name = "";
		for(int i = length-1; i >= 0; i--)
		{
			name += dis.readChar();
		}
		String weapon = "";
		length = dis.readInt();
		for(int i = length-1; i >= 0; i--)
		{
			weapon += dis.readChar();
		}
		double life = dis.readDouble();
		double movement = dis.readDouble();
		
		double energyCost = dis.readDouble();
		double metalCost = dis.readDouble();
		double energyDrain = dis.readDouble();
		double metalDrain = dis.readDouble();
		
		double width = dis.readDouble();
		double height = dis.readDouble();
		double depth = dis.readDouble();
		
		int buildTime = dis.readInt();
		
		ArrayList<String> buildTree = new ArrayList<String>();
		length = dis.readInt();
		for(int i = 0; i < length; i++)
		{
			int tempLength = dis.readInt();
			String tempName = "";
			for(int a = 0; a < tempLength; a++)
			{
				tempName+=dis.readChar();
			}
			buildTree.add(tempName);
		}
		
		Weapon w = EngineConstants.weaponFactory.makeWeapon(weapon);
		Unit u = new Unit(name, w, life, movement, energyCost, metalCost, 
				energyDrain, metalDrain, width, height, depth, buildTime);
		u.setBuildTree(buildTree);
		//System.out.println(name+" build tree = "+buildTree);
		return u;
	}
	private static Unit readUnitV2(DataInputStream dis) throws IOException
	{
		int length = dis.readInt();
		String name = "";
		for(int i = length-1; i >= 0; i--)
		{
			name += dis.readChar();
		}
		String weapon = "";
		length = dis.readInt();
		for(int i = length-1; i >= 0; i--)
		{
			weapon += dis.readChar();
		}
		double life = dis.readDouble();
		double movement = dis.readDouble();
		
		double energyCost = dis.readDouble();
		double metalCost = dis.readDouble();
		double energyDrain = dis.readDouble();
		double metalDrain = dis.readDouble();
		
		double width = dis.readDouble();
		double height = dis.readDouble();
		double depth = dis.readDouble();
		
		ArrayList<String> buildTree = new ArrayList<String>();
		length = dis.readInt();
		for(int i = 0; i < length; i++)
		{
			int tempLength = dis.readInt();
			String tempName = "";
			for(int a = 0; a < tempLength; a++)
			{
				tempName+=dis.readChar();
			}
			buildTree.add(tempName);
		}
		
		Weapon w = EngineConstants.weaponFactory.makeWeapon(weapon);
		Unit u = new Unit(name, w, life, movement, energyCost, metalCost, 
				energyDrain, metalDrain, width, height, depth, 7);
		u.setBuildTree(buildTree);
		//System.out.println(name+" build tree = "+buildTree);
		return u;
	}
}
