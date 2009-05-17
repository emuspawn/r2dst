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
			int length = dis.readInt();
			String name = "";
			for(int i = length-1; i >= 0; i--)
			{
				name += dis.readChar();
			}
			int life = dis.readInt();
			int movement = dis.readInt();
			int width = dis.readInt();
			int height = dis.readInt();
			
			Weapon w = new TestWeapon();
			u = new Unit(name, null, location, w, life, movement, 0, 0, 0, 0, width, 0, height);
		}
		catch(IOException e){}
		return u;
	}
}
