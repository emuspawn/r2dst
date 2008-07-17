package world.pathFinder;

import world.unit.*;
import utilities.Location;

public class DirectMovementPF extends PathFinder
{
	public DirectMovementPF(){}
	public Path findPath(Location destination, Unit u)
	{
		Path p = new Path();
		p.addLocationToPath(destination);
		return p;
	}
}
