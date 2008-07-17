package world;

import world.unit.*;
import world.terrain.*;

public class World
{
	UnitEngine ue;
	int mapWidth = 2000;
	int mapHeight = 2000;
	Terrain[] terrain = new Terrain[10];
	
	public World()
	{
		ue = new UnitEngine(this);
	}
	public UnitEngine getUnitEngine()
	{
		return ue;
	}
	public int getMapHeight()
	{
		return mapHeight;
	}
	public int getMapWidth()
	{
		return mapWidth;
	}
	public void performWorldFunctions()
	{
		ue.performUnitFunctions();
	}
	public Terrain[] getTerrain()
	{
		return terrain;
	}
}
