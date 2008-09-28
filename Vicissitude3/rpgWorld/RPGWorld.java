package rpgWorld;

import rpgWorld.unit.*;
import graphics.Camera;

import controller.RPGController;

/*
 * the main world, if server wise this is run on the server, client runs RPGClientWorld,
 * if single player, this is run but still comunicates to RPGClientWorld first then the
 * draw functions get data from that
 * 
 * draw functions get data from RPGClientWorld only so that between networked and single
 * player games all that needs to change is how the information gets from the main world
 * to the client world
 */

public class RPGWorld
{
	//RPGController c;
	UnitEngine ue;
	
	
	public RPGWorld()
	{
		ue = new UnitEngine();
	}
	public Unit[] getVisibleUnits(Camera c)
	{
		Unit[] u = ue.getUnits();
		Unit[] v = new Unit[1]; //visible
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				if(c.getOnScreen(u[i].getBounds()))
				{
					v = enlargeUnitArray(v);
					v[v.length-1] = u[i];
				}
			}
		}
		return v;
	}
	public Unit[] enlargeUnitArray(Unit[] u)
	{
		Unit[] temp = new Unit[u.length+1];
		for(int i = 0; i < u.length; i++)
		{
			temp[i] = u[i];
		}
		return temp;
	}
}
