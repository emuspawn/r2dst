package pathFinder.pathFinders;

import java.awt.Rectangle;
import java.util.ArrayList;

import pathFinder.Path;
import pathFinder.PathFinder;
import utilities.Location;

public class DirectMovePF extends PathFinder
{
	public DirectMovePF(int mapWidth, int mapHeight)
	{
		super(mapWidth, mapHeight);
	}
	public Path findPath(Location destination, Location start, ArrayList<Rectangle> bounds)
	{
		Path path = new Path();
		path.addLocationToPath(destination);
		return path;
	}
}
