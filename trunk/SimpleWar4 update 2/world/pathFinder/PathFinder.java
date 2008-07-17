package world.pathFinder;

import world.unit.*;
import utilities.Location;

public abstract class PathFinder
{
	public PathFinder(){}
	public abstract Path findPath(Location destination, Unit u);
}
