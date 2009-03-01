package customAI;

import world.owner.Owner;
import pathFinder.PathFinder;
import world.WorldOverlay;
import ai.AI;
import java.util.ArrayList;
import world.unit.FriendlyUnitMask;
import utilities.Location;
import java.util.Random;

public final class TestAI2 extends AI
{
	ArrayList<Location> l;
	
	boolean buildingHQ = false;
	
	int hqs;
	int maxHQs = 6;
	
	public TestAI2(Owner o, WorldOverlay wo, PathFinder pf)
	{
		super(o, wo, pf);
	}
	private void countUnits()
	{
		hqs = 0;
		ArrayList<FriendlyUnitMask> u = wo.getFriendlyUnits(o);
		for(int i = u.size()-1; i >= 0; i --)
		{
			if(u.get(i).getName() == "hq")
			{
				hqs++;
			}
		}
	}
	public void performAIFunctions()
	{
		countUnits();
		buildingHQ = false;
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
			else if(u.get(i).is("hq") && !buildingHQ)
			{
				performHQFunctions(u.get(i));
				buildingHQ = true;
			}
		}
	}
	private void performWorkerFunctions(FriendlyUnitMask u)
	{
		if(o.getUnitCount() < o.getCurrentUnitMax())
		{
			int index = gatherClosestResource(u, l);
			if(index >= 0)
			{
				l.remove(index);
			}
		}
		else if(hqs <= maxHQs)
		{
			//this.build("hq", u);
		}
	}
	private void performHQFunctions(FriendlyUnitMask u)
	{
		build("worker", u);
	}
}
