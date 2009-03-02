package world.terrain;

import world.Element;
import utilities.Location;

public abstract class Terrain extends Element
{
	public Terrain(String name, Location location, int width, int height)
	{
		super(name, location, width, height);
		impassable = true;
	}
}
