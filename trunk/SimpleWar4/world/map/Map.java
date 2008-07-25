package world.map;

import world.unit.*;
import world.terrain.*;
import world.controller.*;

//stores the data for an entire map

public class Map
{
	Unit[] u;
	protected int maxControllers;
	protected ControllerStartLocation[] csl;
	protected int mapWidth;
	protected int mapHeight;
	Terrain[] t = new Terrain[10];
	
	public Map(String name)
	{
		//uses the name string to load a map
	}
	public int getMapWidth()
	{
		return mapWidth;
	}
	public int getMapHeight()
	{
		return mapHeight;
	}
	public Terrain[] getTerrain()
	{
		return t;
	}
	public void setMaxControllers(int setter)
	{
		maxControllers = setter;
		csl = new ControllerStartLocation[setter];
	}
	public ControllerStartLocation[] getControllerStartLocations()
	{
		return csl;
	}
}
