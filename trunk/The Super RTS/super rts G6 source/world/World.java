package world;

import world.resource.Resource;
import driver.GameConstants;
import java.util.ArrayList;
import java.util.Random;
import world.resource.resources.*;
import utilities.Location;
import map.Map;

/*
 * regulates world characteristics such as resources
 */

public class World
{
	Resource[] resources;
	ArrayList<Resource> r = new ArrayList<Resource>();
	Map m;
	
	public World(Map m)
	{
		this.m = m;
		resources = new Resource[1];
		resources[0] = new GreenCircle(new Location(0, 0));
	}
	public void performWorldFunctions()
	{
		Random random = new Random();
		if(random.nextInt(GameConstants.resourceSpawnRate) == 1) //nextInt(30)
		{
			int x = random.nextInt(m.getMapWidth());
			int y = random.nextInt(m.getMapHeight());
			//int x = random.nextInt(300);
			//int y = random.nextInt(300);
			r.add(new GreenCircle(new Location(x, y)));
			//System.out.println("resource created");
		}
	}
	public Resource[] getResourceList()
	{
		return resources;
	}
	public ArrayList<Resource> getResources()
	{
		return r;
	}
}
