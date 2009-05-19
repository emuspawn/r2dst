package io;

import java.io.DataInputStream;
import java.io.IOException;
import utilities.Location;
import world.shot.weapon.TestWeapon;
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
	public static Unit readUnit(DataInputStream dis, Location location)
	{
		Unit u = null;
		try
		{
			int version = dis.readInt();
			if(version == 2)
			{
				u = readUnitV2(dis, location);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("IO exception, returning null");
		}
		return u;
	}
	private static Unit readUnitV2(DataInputStream dis, Location location) throws IOException
	{
		int length = dis.readInt();
		String name = "";
		for(int i = length-1; i >= 0; i--)
		{
			name += dis.readChar();
		}
		int life = dis.readInt();
		int movement = dis.readInt();
		int width = dis.readInt();
		int depth = dis.readInt();
		
		double height = 7;
		
		Weapon w = new TestWeapon();
		Location l = new Location(location.x, location.y+height/2, location.z);
		return new Unit(name, null, l, w, life, movement, 0, 0, 0, 0, width, height, depth);
	}
}
