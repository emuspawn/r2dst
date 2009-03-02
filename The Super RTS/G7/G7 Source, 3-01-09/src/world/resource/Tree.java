package world.resource;

import java.awt.Color;
import utilities.Location;
import world.resource.spawnFormat.TreeSpawn;

public class Tree extends Resource
{
	public Tree(Location location)
	{
		super(location, "tree", 10, new Color(50, 170, 40), 15, 3);
		setSpawnFormat(new TreeSpawn(this));
	}
}
