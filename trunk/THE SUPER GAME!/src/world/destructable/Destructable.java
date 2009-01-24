package world.destructable;

import java.io.Serializable;

import world.Element;
import world.World;
import utilities.Location;

public abstract class Destructable extends Element implements Serializable
{
	private static final long serialVersionUID = 1L;
	boolean dead = false; //a dead unit is removed from the list of units
	int life;
	
	public Destructable(String name, Location l, int width, int height)
	{
		super(name, l, width, height);
	}
	public boolean isDead()
	{
		return dead;
	}
	public int getLife()
	{
		return life;
	}
	public abstract void destroy(World w); //action performed when the destructable is destroyed
}