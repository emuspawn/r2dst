package rpgWorld;

import rpgWorld.unit.*;
import tileSystem.TileSystem;
import controller.RPGController;
import rpgWorld.unit.unitType.*;
import utilities.Map;
import rpgWorld.loader.WorldReader;

/*
 * the world the client is connected to, gets key information from the main world RPGWorld,
 * handles cleint level events such as animations, weapon and spell recharge times
 * 
 * holds terrain data (TileSystem)
 */

public class RPGClientWorld implements Map
{
	RPGWorld w;
	RPGController c;
	Unit hero; //the clients hero, position and other data sent to the main world
	
	int mapWidth;
	int mapHeight;
	TileSystem ts;
	
	public RPGClientWorld(RPGWorld w, RPGController c)
	{
		//single player, direct connection to the main world
		this.w = w;
		this.c = c;
		hero = new Hero();
		ts = new TileSystem(0, 0);
		
		WorldReader.loadWorld("Untitled.wrld", ts, this);
	}
	public Unit getHero()
	{
		return hero;
	}
	public Unit[] getVisibleUnits()
	{
		if(w != null)
		{
			return w.getVisibleUnits(c.getCamera());
		}
		return null;
	}
	public TileSystem getTileSystem()
	{
		return ts;
	}
	public void setMapWidth(int setter)
	{
		mapWidth = setter;
	}
	public void setMapHeight(int setter)
	{
		mapHeight = setter;
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
