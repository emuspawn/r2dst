package ai.humanAI.humanAIs;

import graphics.GLCamera;
import utilities.Location;
import world.World;
import world.owner.Owner;
import world.unit.Unit;
import ai.humanAI.BasicHumanAI;

public class TestHumanAI extends BasicHumanAI
{
	public TestHumanAI(Owner o, World w, GLCamera c)
	{
		super(o, w, c);
	}
	protected void orderUnit(Unit u, Location l)
	{
		if(u.isSelected())
		{
			/*Location loc = new Location(Math.random()*w.getWidth()-w.getWidth()/2, 0, 
					Math.random()*w.getHeight()-w.getHeight()/2);*/
			if(l != null)
			{
				forceMoveUnit(u, l);
			}
			//Location loc = new Location(200, 200, 200);
			//moveUnit(u, loc);
		}
	}
}
