package world.shot.shotPath;

import utilities.Location;

/**
 * describes the path of the shot
 * @author Jack
 *
 */
public abstract class ShotPath
{
	/**
	 * gets the next location along the path of the shot
	 * @param current the shot's current location
	 * @param target the target of the shot
	 * @param movement the shot's movement rate
	 * @return returns the next location of the shot along its path
	 */
	public abstract Location determineNextLocation(Location current, Location target, double movement);
}
