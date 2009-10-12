package pathFinder;

import java.awt.Rectangle;
import java.util.ArrayList;
import utilities.Location;

public abstract class PathFinder
{
	protected int mapWidth;
	protected int mapHeight;
	public PathFinder(int mapWidth, int mapHeight)
	{
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}
	public abstract Path findPath(Location destination, Location start, ArrayList<Rectangle> bounds);
}
