package world.spawn.spawnFormat;

import world.resource.*;
import utilities.Location;

public class TreeSpawn extends SpawnFormat
{
	public TreeSpawn(Tree t)
	{
		super(t, 100, 100, 50, 8);
	}
	public Resource spawnResource(Location l)
	{
		return new Tree(l);
	}
}
