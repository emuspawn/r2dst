package world.resource.spawnFormat;

import world.resource.*;
import utilities.Location;

/**
 * how trees spawn
 * @author Jack
 *
 */
public class TreeSpawn extends SpawnFormat
{
	public TreeSpawn(Tree t)
	{
		super(t, 150, 150, 200, 100, .1);
	}
	public Resource spawnResource(Location l)
	{
		return new Tree(l);
	}
}
