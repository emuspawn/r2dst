package ai.computerAI.computerAIs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import sgEngine.SGEngine;
import utilities.Location;
import world.World;
import world.owner.Owner;
import world.unit.Unit;
import ai.computerAI.ComputerAI;

public class RapeBot extends ComputerAI
{
	boolean firstRun = true, buildFirst = true;
	Location startLoc, closestEnemy, enemyStartLoc;
	
	int workers = 0;
	int lightTanks = 0;
	int factories = 0;
	int refineries = 0;
	int sctpPlants = 0;
	
	
	public RapeBot(Owner o, World w, SGEngine sge)
	{
		super(o, w, sge);
	}
	public void performAIFunctions()
	{
		LinkedList<Unit> units = getUnits();
		Iterator<Unit> i = units.iterator();
		HashMap<String, LinkedList<Unit>> enemies = getEnemyUnits();
		countUnits();
		
		if (firstRun)
		{
			startLoc = units.get(0).getLocation();
			Iterator<String> k = enemies.keySet().iterator();
			if(k.hasNext())
			{
				Iterator<Unit> j = enemies.get(k.next()).iterator();
				if(j.hasNext())
				{
					enemyStartLoc = j.next().getLocation();
				}
			}
			buildAt("sctp plant", units.get(0), units.get(0).getLocation());
			firstRun = false;
		}
		
		getClosestEnemy(enemies);
		
		while(i.hasNext())
		{
			Unit u = i.next();
			if(u.getAction().getName().equals("idle"))
				controlUnit(u);
		}	
	}
	private void countUnits()
	{
		workers = 0;
		lightTanks = 0;
		factories = 0;
		refineries = 0;
		sctpPlants = 0;
		LinkedList<Unit> units = getUnits();
		Iterator<Unit> i = units.iterator();
		while(i.hasNext())
		{
			Unit u = i.next();
			if(u.is("worker"))
			{
				workers++;
			}
			else if(u.is("light tank"))
			{
				lightTanks++;
			}
			else if(u.is("factory"))
			{
				factories++;
			}
			else if(u.is("refinery"))
			{
				refineries++;
			}
			else if(u.is("sctp plant"))
			{
				sctpPlants++;
			}
		}	
		System.out.println(workers);
		System.out.println(lightTanks);
		System.out.println(factories);
		System.out.println(refineries);
		System.out.println(sctpPlants);
	}
	private void getClosestEnemy(HashMap<String, LinkedList<Unit>> enemies)
	{
		double shortest = enemyStartLoc.distanceTo(startLoc);
		Iterator<String> ki = enemies.keySet().iterator();
		while(ki.hasNext())
		{
			Iterator<Unit> q = enemies.get(ki.next()).iterator();
			while(q.hasNext())
			{
				Unit u = q.next();
				double dist = u.getLocation().distanceTo(startLoc);
				if (u.getMovement() == 0)
					dist *= 0.5;
				if (dist < shortest)
				{
					shortest = (int) dist;
					closestEnemy = u.getLocation();
				}
			}
		}
		if (closestEnemy == null)
			closestEnemy = enemyStartLoc;
	}
	
	private void controlUnit(Unit u)
	{
		if(u.is("worker"))
		{
			Location l = getCloseLocation(u.getLocation(), 50);
			if(sctpPlants == 0)
			{
				buildAt("sctpPlant", u, l);
			}
			else if(refineries == 0)
			{
				buildAt("refinery", u, l);
				refineries++;
			}
			else if(factories == 0 || factories < sctpPlants+refineries)
			{
				buildAt("factory", u, l);
				factories++;
			}
			else if(refineries < sctpPlants)
			{
				buildAt("refinery", u, l);
				refineries++;
			}
			else
				buildAt("sctpPlant", u, l);

		}
		else if(u.is("factory"))
		{
			if(workers < 4)
				buildAt("worker", u, u.getLocation());
			else if(lightTanks < workers*4)
				buildAt("light tank", u, u.getLocation());
			else
				buildAt("worker", u, u.getLocation());
		}
		else if(u.is("light tank"))
		{
			attackWithUnit(u);	
		}
	}
	
	private Location getCloseLocation(Location l, int distance)
	{
		return new Location((l.x-distance)+Math.random()*distance*2, 0, (l.z-distance)+Math.random()*distance*2);
	}
	private void attackWithUnit(Unit u)
	{
		Location l = getCloseLocation(closestEnemy, 40);
		moveUnit(u, l);
	}
}
