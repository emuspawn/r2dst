package io;

import java.io.DataInputStream;
import java.io.IOException;
import sgEngine.EngineConstants;
import world.shot.Shot;
import world.shot.weapon.Weapon;

public final class WeaponReader
{
	public static Weapon readWeapon(DataInputStream dis)
	{
		Weapon w = null;
		try
		{
			int version = dis.readInt();
			if(version == 1)
			{
				return readWeaponV1(dis);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("io exception caught, continuing...");
		}
		return w;
	}
	private static Weapon readWeaponV1(DataInputStream dis) throws IOException
	{
		int length = dis.readInt();
		String name = "";
		for(int i = length-1; i >= 0; i--)
		{
			name+=dis.readChar();
		}
		length = dis.readInt();
		String shot = "";
		for(int i = length-1; i >= 0; i--)
		{
			shot+=dis.readChar();
		}
		double range = dis.readDouble();
		int reload = dis.readInt();
		
		/*File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"shots"+
				System.getProperty("file.separator")+shot+".shot");
		FileInputStream fis = new FileInputStream(f);
		DataInputStream shotdis = new DataInputStream(fis);
		Shot s = ShotReader.readShot(shotdis);*/
		Shot s = EngineConstants.shotFactory.makeShot(shot);
		
		return new Weapon(name, s, range, reload);
	}
}
