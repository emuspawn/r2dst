package customAI;

import world.owner.Owner;
import pathFinder.PathFinder;
import world.BuildEngineOverlay;
import world.WorldOverlay;
import ai.AI;
import java.util.ArrayList;
import world.unit.FriendlyUnitMask;
import utilities.Location;
import java.util.Random;

public final class TestAI extends AI
{
	ArrayList<Location> l;
	
	public TestAI(Owner o, WorldOverlay wo, BuildEngineOverlay beo, PathFinder pf)
	{
		super(o, wo, beo, pf);
	}
	public void performAIFunctions()
	{
		l = wo.getVisibleResources(o);
		ArrayList<FriendlyUnitMask> u = wo.getFriendlyUnits(o);
		//System.out.println(o.getUnitCount()+" / "+o.getCurrentUnitMax());
		for(int i = 0; i < u.size(); i++)
		{
			//moveUnit(u.get(i), new Location(new Random().nextDouble()*wo.getMapWidth(), new Random().nextDouble()*wo.getMapHeight()));
			if(u.get(i).is("worker"))
			{
				performWorkerFunctions(u.get(i));
			}
			else if(u.get(i).is("hq"))
			{
				performHQFunctions(u.get(i));
			}
		}
	}
	private void performWorkerFunctions(FriendlyUnitMask u)
	{
		int index = gatherClosestResource(u, l);
		if(index >= 0)
		{
			l.remove(index);
		}
	}
	private void performHQFunctions(FriendlyUnitMask u)
	{
		build("worker", u);
	}
}
