package ai.computerAI.computerAIs;

import java.util.Iterator;
import java.util.LinkedList;
import sgEngine.SGEngine;
import utilities.Location;
import world.World;
import world.owner.Owner;
import world.unit.Unit;
import ai.computerAI.ComputerAI;

public class TesterAI extends ComputerAI
{
	public TesterAI(Owner o, World w, SGEngine sge)
	{
		super(o, w, sge);
	}
	public void performAIFunctions()
	{
		LinkedList<Unit> units = getUnits();
		Iterator<Unit> i = units.iterator();
		while(i.hasNext())
		{
			Unit u = i.next();
			if(u.getAction().getName().equals("idle"))
			{
				buildRandomly(u);
				randomlyMoveUnit(u);
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
	private void randomlyMoveUnit(Unit u)
	{
		Location l = getRandomMapLocation();
		//System.out.println(l);
		moveUnit(u, l);
	}
}
