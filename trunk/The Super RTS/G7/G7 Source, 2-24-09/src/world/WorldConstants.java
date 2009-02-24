package world;

import java.awt.Color;

public final class WorldConstants
{
	public final static int gatherRange = 10;
	public final static int unloadRange = 10;
	public final static int maxUnitCount = 100;
	public final static int resourceSpawnRate = 2;
	public final static int maxResources = 400; //max resources that are on the map at a given time
	public final static int initialPartitionSize = 50; //partition size of the dynamic map
	
	public final static Color[] teamColors = {Color.red, Color.blue, 
		Color.green, Color.cyan, Color.yellow, Color.orange, 
		Color.magenta, Color.pink, Color.white};
}
