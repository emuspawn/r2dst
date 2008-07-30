package world.map;

import utilities.Location;
import world.controller.*;

public class TestMap extends Map
{
	public TestMap()
	{
		super(null);
		mapWidth = 5000;
		mapHeight = 5000;
		setMaxControllers(2);
		csl[0] = new ControllerStartLocation(1, new Location(100, 100), new Location(400, 400));
		csl[1] = new ControllerStartLocation(2, new Location(4900, 4900), new Location(4400, 4900));
	}
}
