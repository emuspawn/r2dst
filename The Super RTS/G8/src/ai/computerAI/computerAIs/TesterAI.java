package ai.computerAI.computerAIs;

import java.util.Iterator;
import java.util.LinkedList;

import sgEngine.userAction.KeyPress;
import sgEngine.userAction.KeyRelease;
import utilities.Location;
import world.World;
import world.owner.Owner;
import world.unit.Unit;
import ai.computerAI.ComputerAI;

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
			if(u.getAction().getName().equals("idle"))
			{
				Location l = new Location(Math.random()*w.getWidth()-w.getWidth()/2, u.getRestingHeight(),
					Math.random()*w.getDepth()-w.getDepth()/2);
				//System.out.println(l);
				moveUnit(u, l);
			}
		}
		//System.out.println(units.size());
	}
}
