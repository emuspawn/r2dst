package ai.humanAI.basicHumanAI;

import graphics.GLCamera;
import sgEngine.SGEngine;
import utilities.Location;
import world.World;
import world.owner.Owner;
import world.unit.Unit;

public class TestHumanAI extends BasicHumanAI
{
	public TestHumanAI(Owner o, World w, SGEngine sge, GLCamera c)
	{
		super(o, w, sge, c);
	}
	protected void orderUnit(Unit u, Location l)
	{
		if(u.isSelected())
		{
			/*Location loc = new Location(Math.random()*w.getWidth()-w.getWidth()/2, 0, 
					Math.random()*w.getHeight()-w.getHeight()/2);*/
			if(l != null)
			{
				Location location = new Location(l.x, l.y+u.getRestingHeight(), l.z);
				forceMoveUnit(u, location);
			}
			//Location loc = new Location(200, 200, 200);
			//moveUnit(u, loc);
		}
	}
}
