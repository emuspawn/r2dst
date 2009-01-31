package connection;

import world.destructable.Unit;
import io.AccountReader;
import java.io.*;
import graphics.Camera;
import utilities.Location;

/*
 * users only loaded from world, never client side
 */

public class User
{
	Unit u;
	int gold;
	
	//the user's screen dimensions
	int width;
	int height;
	
	public User(DataInputStream dis)
	{
		//the dis is for the user account file to be loaded to this user
		try
		{
			Account a = AccountReader.readAccount(dis);
			gold = a.getGold();
			u = new Unit(a.getName(), new Location(300, 300), 30, 30);
		}
		catch(IOException e)
		{
			System.out.println("io exception");
			e.printStackTrace();
		}
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public void setHeight(int height)
	{
		this.height = height;
	}
	public Unit getUnit()
	{
		return u;
	}
	public Camera getCamera()
	{
		Camera c = new Camera(width, height);
		c.centerOn(u.getLocation());
		return c;
	}
}
