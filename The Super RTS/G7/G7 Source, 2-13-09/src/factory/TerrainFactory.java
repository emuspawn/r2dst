package factory;

import utilities.Location;
import world.terrain.*;

public class TerrainFactory
{
	public static Terrain makeTerrain(String name, Location l, int width, int height)
	{
		if(name.equalsIgnoreCase("hard rock"))
		{
			return new HardRock(l, width, height);
		}
		else if(name.equalsIgnoreCase("water"))
		{
			return new Water(l, width, height);
		}
		return null;
	}
}
