package permanents.factories;

import utilities.Location;
import world.permanent.*;
import permanents.terrain.*;

public class TerrainFactory
{
	public static Permanent createTerrain(Location l, String s)
	{
		if(s.equalsIgnoreCase("dirt"))
		{
			return new Dirt(l);
		}
		else if(s.equalsIgnoreCase("hard stone"))
		{
			return new HardStone(l);
		}
		return null;
	}
}
