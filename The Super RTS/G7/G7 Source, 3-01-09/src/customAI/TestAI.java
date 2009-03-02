package customAI;

import world.owner.Owner;
import pathFinder.PathFinder;
import world.WorldOverlay;
import ai.AI;
import java.util.ArrayList;
import world.unit.FriendlyUnitMask;
import utilities.Location;
import java.util.Random;

public class TestAI extends AI
{
	ArrayList<Location> l;
	
	boolean buildingHQ = false;
	boolean buildingBarracks = false;
	
	int hqs;
	int maxHQs = 2;
	int barracks;
	int maxBarracks = 2;
	int workers;
	int maxWorkers = 20;
	
	public TestAI(Owner o, WorldOverlay wo, PathFinder pf)
	{
		super(o, wo, pf);
	}
	private void countUnits()
	{
		hqs = 0;
		barracks = 0;
		workers = 0;
		ArrayList<FriendlyUnitMask> u = wo.getFriendlyUnits(o);
		for(int i = u.size()-1; i >= 0; i --)
		{
			if(u.get(i).getName() == "hq")
			{
				hqs++;
			}
			else if(u.get(i).getName() == "barracks")
			{
				barracks++;
			}
			else if(u.get(i).getName() == "workers")
			{
				workers++;
			}
		}
	}
	public void performAIFunctions()
	{
		countUnits();
		
		buildingHQ = false;
		buildingBarracks = false;
		
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
			else if(u.get(i).is("barracks"))
			{
				performBarracksFunctions(u.get(i));
				buildingHQ = true;
			}
			else if(u.get(i).is("fighter"))
			{
				performFighterFunctions(u.get(i));
			}
		}
	}
	private void performFighterFunctions(FriendlyUnitMask u)
	{
		Random r = new Random();
		double x = wo.getMapWidth()*r.nextDouble();
		double y = wo.getMapHeight()*r.nextDouble();
		moveUnit(u, new Location(x, y));
	}
	private void performBarracksFunctions(FriendlyUnitMask u)
	{
		this.build("fighter", u);
	}
	private void performWorkerFunctions(FriendlyUnitMask u)
	{
		boolean working = false;
		if(hqs <= maxHQs && !buildingHQ)
		{
			if(build("hq", u))
			{
				buildingHQ = true;
				hqs++;
				working = true;
			}
		}
		else if(barracks <= maxBarracks && !buildingBarracks && !working)
		{
			if(this.build("barracks", u))
			{
				buildingBarracks = true;
				//System.out.println("barracks built");
				working = true;
			}
		}
		if(!working)
		{
			int index = gatherClosestResource(u, l);
			if(index >= 0)
			{
				l.remove(index);
			}
			else
			{
				Random r = new Random();
				double x = wo.getMapWidth()*r.nextDouble();
				double y = wo.getMapHeight()*r.nextDouble();
				moveUnit(u, new Location(x, y));
			}
		}
	}
	private void performHQFunctions(FriendlyUnitMask u)
	{
		if(workers < maxWorkers)
		{
			if(build("worker", u))
			{
				workers++;
			}
		}
	}
}
