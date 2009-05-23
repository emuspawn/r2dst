package world.unit;

import io.UnitReader;
import java.io.*;
import java.util.HashMap;

import utilities.Location;
import world.owner.Owner;

/**
 * loads all the units beforehand for easy access in game
 * @author Jack
 *
 */
public class UnitFactory
{
	private HashMap<String, Unit> u = new HashMap<String, Unit>();
	
	/**
	 * creates a new unit factory that automatically loads all units in the units folder
	 */
	public UnitFactory()
	{
		System.out.println("loading units...");
		File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"units");
		load(f);
		System.out.println("done");
	}
	/**
	 * recursively loads all units in the passed subdirectory
	 * @param f the starting directory
	 */
	private void load(File f)
	{
		if(f.isDirectory())
		{
			System.out.println("loading "+f.getAbsolutePath()+"...");
			File[] files = f.listFiles();
			for(int i = 0; i < files.length; i++)
			{
				load(files[i]);
			}
		}
		else
		{
			if(f.getName().endsWith(".unit"))
			{
				System.out.print("loading "+f.getName()+"...");
				try
				{
					FileInputStream fis = new FileInputStream(f);
					DataInputStream dis = new DataInputStream(fis);
					Unit unit = UnitReader.readUnit(dis);
					u.put(unit.getName(), unit);
					System.out.println(" done");
				}
				catch(IOException e)
				{
					System.out.println(" FAILED");
				}
			}
		}
	}
	/**
	 * creates a unit
	 * @param name the name of the unit to be created
	 * @param owner
	 * @param l
	 * @return returns the newly created unit
	 */
	public Unit makeUnit(String name, Owner owner, Location l)
	{
		return new Unit(u.get(name), owner, l);
	}
}
