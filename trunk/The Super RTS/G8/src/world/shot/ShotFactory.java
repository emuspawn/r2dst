package world.shot;

import io.ShotReader;
import java.io.*;
import java.util.HashMap;

/**
 * loads all the shots beforehand for easy access in game
 * @author Jack
 *
 */
public class ShotFactory
{
	private HashMap<String, Shot> s = new HashMap<String, Shot>();
	
	/**
	 * creates a new shot factory that automatically loads all shots in the shots folder
	 */
	public ShotFactory()
	{
		System.out.println("loading shots...");
		File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"shots");
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
			if(f.getName().endsWith(".shot"))
			{
				System.out.print("loading "+f.getName()+"...");
				try
				{
					FileInputStream fis = new FileInputStream(f);
					DataInputStream dis = new DataInputStream(fis);
					Shot shot = ShotReader.readShot(dis);
					s.put(shot.getName(), shot);
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
	 * creates a shot
	 * @param name the name of the shot to be created
	 * @return returns the newly created shot
	 */
	public Shot makeShot(String name)
	{
		Shot s = this.s.get(name);
		return new Shot(s.getName(), s.getDamage(), s.getMovement(), s.getWidth(), s.getHeight(), s.getDepth());
	}
}
