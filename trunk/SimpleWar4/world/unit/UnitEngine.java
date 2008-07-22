package world.unit;

import world.pathFinder.*;
import world.pathFinder.astarv1.AStarV1PF;
import world.pathFinder.astarv2.AStarV2PF;
import world.pathFinder.astarv3.AStarV3PF;
import world.pathFinder.astarv4.AStarV4PF;
import world.pathFinder.astarv5.AStarV5PF;
import world.pathFinder.KylePF.KylePF;
import world.World;
import java.awt.Point;
import utilities.Location;
import world.unit.unitMover.*;

public class UnitEngine
{
	Unit[] u = new Unit[30];
	
	PathFinder[] pathFinders = new PathFinder[10];
	int pathFinderUsed = 6;
	
	UnitMover[] um = new UnitMover[5];
	int unitMoverUsed = 1;
	
	public UnitEngine(World w)
	{
		setupPathFinders(w);
		setupUnitMovers();
	}
	private void setupUnitMovers()
	{
		um[0] = new MoverseV1();
		um[1] = new MoverseV2();
	}
	private void setupPathFinders(World w)
	{
		pathFinders[0] = new DirectMovementPF();
		pathFinders[1] = new AStarV1PF(w);
		pathFinders[2] = new AStarV2PF(w);
		pathFinders[3] = new KylePF(w);
		pathFinders[4] = new AStarV3PF(w);
		pathFinders[5] = new AStarV4PF(w);
		pathFinders[6] = new AStarV5PF(w);
	}
	public void registerUnit(Unit unit)
	{
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] == null)
			{
				u[i] = unit;
				break;
			}
		}
	}
	public synchronized void findUnitPaths(Point p)
	{
		Location destination = new Location(p);
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				if(u[i].getHighlighted())
				{
					System.out.println("path finder used = "+pathFinderUsed);
					u[i].setPointMovingTo(null);
					//u[i].setDestination(destination);
					System.out.println("order sent to find path");
					u[i].setPath(pathFinders[pathFinderUsed].findPath(destination, u[i]));
					u[i].setMoving(true);
				}
			}
		}
	}
	public Unit[] getUnits()
	{
		return u;
	}
	public void performUnitFunctions()
	{
		testForUnitDeaths();
		um[unitMoverUsed].moveUnits(u);
	}
	private void testForUnitDeaths()
	{
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				if(u[i].getLife() <= 0)
				{
					u[i] = null;
				}
			}
		}
	}
}
