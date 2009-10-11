package gameEngine.ai.computerAI.computerAIs;

import gameEngine.ai.computerAI.ComputerAI;
import gameEngine.world.World;
import gameEngine.world.owner.Owner;
import gameEngine.world.unit.Unit;
import java.util.Iterator;
import java.util.LinkedList;
import utilities.Location;

public class TesterAI extends ComputerAI
{
	public TesterAI(Owner o, World w)
	{
		super(o, w);
	}
	public void performAIFunctions()
	{
		LinkedList<Unit> units = getUnits();
		Iterator<Unit> i = units.iterator();
		while(i.hasNext())
		{
			Unit u = i.next();
			u.setSelected(true);
			if(u.getCurrentAction() == null)
			{
				randomlyMoveUnit(u);
			}
		}
	}
	/**
	 * gets a random location within the map bounds
	 * @return returns a random location inside the game world
	 */
	private Location getRandomMapLocation()
	{
		return new Location(Math.random()*w.getMapWidth(), Math.random()*w.getMapHeight());
	}
	private void randomlyMoveUnit(Unit u)
	{
		Location l = getRandomMapLocation();
		//System.out.println(l);
		moveUnit(u, l.x, l.y);
	}
}
