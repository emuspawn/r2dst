package world.shot.weapon;

import io.WeaponReader;
import java.io.*;
import java.util.HashMap;

/**
 * loads all the weapons beforehand for easy access in game
 * @author Jack
 *
 */
public class WeaponFactory
{
	private HashMap<String, Weapon> w = new HashMap<String, Weapon>();
	
	/**
	 * creates a new weapon factory that automatically loads all weapons in the weapons folder
	 */
	public WeaponFactory()
	{
		System.out.println("loading weapons...");
		File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"weapons");
		load(f);
		System.out.println("done");
	}
	/**
	 * recursively loads all weapons in the passed subdirectory
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
			if(f.getName().endsWith(".weapon"))
			{
				System.out.print("loading "+f.getName()+"...");
				try
				{
					FileInputStream fis = new FileInputStream(f);
					DataInputStream dis = new DataInputStream(fis);
					Weapon weapon = WeaponReader.readWeapon(dis);
					w.put(weapon.getName(), weapon);
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
	 * creates a weapon
	 * @param name the name of the weapon to be created
	 * @return returns the newly created weapon
	 */
	public Weapon makeWeapon(String name)
	{
		Weapon w = this.w.get(name);
		return new Weapon(w.getName(), w.getShot(), w.getRange(), w.getReload());
	}
}
