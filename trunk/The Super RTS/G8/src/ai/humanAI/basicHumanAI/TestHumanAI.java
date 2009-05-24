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
		buildRandomly(u);
		if(u.isSelected())
		{
			if(l != null)
			{
				Location location = new Location(l.x, l.y+u.getRestingHeight(), l.z);
				forceMoveUnit(u, location);
			}
		}
	}
	private void buildRandomly(Unit u)
	{
		Location l = getRandomMapLocation();
		this.buildAt("test building", u, l);
		this.buildAt("test unit 3", u, u.getLocation());
	}
	/**
	 * gets a random location within the map bounds, the height does not matter
	 * because the AI class automatically sends units to the proper resting height
	 * @return
	 */
	private Location getRandomMapLocation()
	{
		return new Location(Math.random()*w.getWidth()-w.getWidth()/2, 0, Math.random()*w.getDepth()-w.getDepth()/2);
	}
}
