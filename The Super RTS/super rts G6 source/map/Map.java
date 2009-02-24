package map;

import utilities.Location;
import java.util.Random;

public class Map
{
	int maxPlayers = 2;
	int mapWidth = 1000;
	int mapHeight = 1000;
	Location[] sl = new Location[maxPlayers]; //start locations
	
	public Map()
	{
		Random r = new Random();
		for(int i = 0; i < maxPlayers; i++)
		{
			sl[i] = new Location(mapWidth*r.nextDouble(), mapHeight*r.nextDouble());
		}
		
		
		
		
		sl[0] = new Location(100, 100);
		sl[1] = new Location(mapWidth-100, mapHeight-100);
	}
	public Map(int maxPlayers, int mapWidth, int mapHeight, Location[] startLocations)
	{
		this.maxPlayers = maxPlayers;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		sl = startLocations;
	}
	public void setMapWidth(int setter)
	{
		mapWidth = setter;
		sl[1] = new Location(mapWidth-100, mapHeight-100);
	}
	public void setMapHeight(int setter)
	{
		mapHeight = setter;
		sl[1] = new Location(mapWidth-100, mapHeight-100);
	}
	public Location[] getStartingLocations()
	{
		return sl;
	}
	public int getMapWidth()
	{
		return mapWidth;
	}
	public int getMapHeight()
	{
		return mapHeight;
	}
}
