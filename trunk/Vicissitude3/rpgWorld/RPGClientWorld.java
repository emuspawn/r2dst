package rpgWorld;

import rpgWorld.unit.*;
import controller.RPGController;
import rpgWorld.unit.unitType.*;

/*
 * the world the client is connected to, gets key information from the main world RPGWorld,
 * handles cleint level events such as animations, weapon and spell recharge times
 */

public class RPGClientWorld
{
	RPGWorld w;
	RPGController c;
	Unit hero; //the clients hero, position and other data sent to the main world
	
	public RPGClientWorld(RPGWorld w)
	{
		//single player, direct connection to the main world
		this.w = w;
		hero = new Hero();
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
}
