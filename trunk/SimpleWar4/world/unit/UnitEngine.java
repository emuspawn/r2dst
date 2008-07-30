package world.unit;

import world.pathFinder.*;
import world.pathFinder.astarv1.AStarV1PF;
import world.pathFinder.astarv2.AStarV2PF;
import world.pathFinder.astarv3.AStarV3PF;
import world.pathFinder.astarv4.AStarV4PF;
import world.pathFinder.astarv5.AStarV5PF;
import world.pathFinder.astarv6.AStarV6PF;
import world.pathFinder.KylePF.KylePF;
import world.World;
import java.awt.Point;
import utilities.Location;
import world.unit.unitMover.*;
import world.controller.*;
import graphics.Camera;

public class UnitEngine
{
	World w;
	Camera camera;
	
	Unit[] u = new Unit[30];
	boolean[] findPath = new boolean[u.length];
	Location[] destinations = new Location[u.length];
	
	PathFinder[] pathFinders = new PathFinder[10];
	int pathFinderUsed = 7;
	
	UnitMover[] um = new UnitMover[5];
	int unitMoverUsed = 1;
	
	public UnitEngine(World w, Camera camera)
	{
		this.w = w;
		this.camera = camera;
		setupPathFinders(w);
		setupUnitMovers();
		
		for(int i = 0; i < findPath.length; i++)
		{
			findPath[i] = false;
		}
	}
	public void spawnStartUnits()
	{
		//to be run at the start of a new game
		Controller[] c = w.getControllers();
		ControllerStartLocation[] csl = w.getMap().getControllerStartLocations();
		for(int i = 0; i < c.length; i++)
		{
			if(c[i] != null)
			{
				for(int a = 0; a < csl.length; a++)
				{
					if(csl[a] != null)
					{
						if(csl[a].getPlayerFor() == c[i].getPlayerNumber())
						{
							registerUnit(c[i].getRace().getDivineAnchor(camera, c[i], csl[a].getDivineAnchorLocation()));
							registerUnit(c[i].getRace().getCastle(camera, c[i], csl[a].getCastleLocation()));
							System.out.println("start units spawned for player "+(i+1));
						}
					}
				}
			}
		}
	}
	private void setupUnitMovers()
	{
		um[0] = new MoverseV1();
		um[1] = new MoverseV2();
	}
	private void setupPathFinders(World w)
	{
		pathFinders[1] = new AStarV1PF(w);
		pathFinders[2] = new AStarV2PF(w);
		pathFinders[3] = new KylePF(w);
		pathFinders[4] = new AStarV3PF(w);
		pathFinders[5] = new AStarV4PF(w);
		pathFinders[6] = new AStarV5PF(w);
		pathFinders[7] = new AStarV6PF(w);
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
	public synchronized void flagUnitsForFindPath(Point p)
	{
		/*
		 * units must be flagged because the mouse listener runs off
		 * a separate thread and it can update a units path while the
		 * unit is moving giving strange results from the unit mover
		 * 
		 * a flag system merely marks the units that a path should be
		 * found for then calculates it once it has already moved them
		 */
		
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				if(u[i].getHighlighted())
				{
					findPath[i] = true;
					destinations[i] = new Location(p);
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
		//findUnitPaths();
	}
	public void findUnitPaths()
	{
		boolean[] temp = new boolean[findPath.length];
		for(int i = 0; i < findPath.length; i++)
		{
			temp[i] = findPath[i];
		}
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				if(u[i].getHighlighted())
				{
					if(temp[i] == true)
					{
						findPath[i] = false;
						//System.out.println("path finder used = "+pathFinderUsed);
						u[i].setPointMovingTo(null);
						//u[i].setDestination(destination);
						//System.out.println("order sent to find path");
						u[i].setPath(pathFinders[pathFinderUsed].findPath(destinations[i], u[i]));
						u[i].setMoving(true);
					}
				}
			}
		}
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
