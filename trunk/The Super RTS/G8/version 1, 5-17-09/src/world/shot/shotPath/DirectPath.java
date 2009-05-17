package world.shot.shotPath;

import utilities.Location;
import utilities.MoverseV3;

public class DirectPath extends ShotPath
{
	public Location determineNextLocation(Location current, Location target, double movement)
	{
		return MoverseV3.getNewLocation(current, target, movement);
	}
}
