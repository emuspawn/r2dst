package world.resource;

import java.awt.Color;
import utilities.Location;
import spawn.spawnFormat.TreeSpawn;

public class Tree extends Resource
{
	public Tree(Location location)
	{
		super(location, "tree", 10, Color.green, 15, 5);
		setSpawnFormat(new TreeSpawn(this));
	}
}
