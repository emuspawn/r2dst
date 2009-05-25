package ai.computerAI.computerAIs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import sgEngine.SGEngine;
import utilities.Location;
import world.World;
import world.owner.Owner;
import world.unit.Unit;
import ai.computerAI.ComputerAI;

public final class SimpleAI extends ComputerAI
{
	ArrayList<Location> buildLocations; //where buildings should be built
	Location attackPoint; //where unitsare to attack
	boolean attackingMovingTarget = false;
	
	//maximums are rough, not exact
	int maxWorkers = 3;
	int workers;
	
	int maxLightTanks = 30;
	int lightTanks = 0;
	
	int maxsctpPlants = 2;
	int sctpPlants;
	
	int maxRefineries = 3;
	int refineries;
	
	int maxFactories = 1;
	int factories;
	
	public SimpleAI(Owner o, World w, SGEngine sge)
	{
		super(o, w, sge);
	}
	public void performAIFunctions()
	{
		determineState();
		LinkedList<Unit> units = getUnits();
		Iterator<Unit> i = units.iterator();
		while(i.hasNext())
		{
			Unit u = i.next();
			if(u.getAction().getName().equals("idle"))
			{
				if(u.is("worker"))
				{
					orderWorker(u);
				}
				else if(u.is("factory"))
				{
					orderFactory(u);
				}
				else if(u.is("light tank"))
				{
					orderLightTank(u);
				}
			}
		}
		//System.out.println("e = "+o.getEnergy()+", m = "+o.getMetal());
	}
	/**
	 * orders the light tanks
	 * @param u
	 */
	private void orderLightTank(Unit u)
	{
		if(attackPoint != null)
		{
			if(attackingMovingTarget)
			{
				forceMoveUnit(u, getRegionLocation(attackPoint, 300, 300));
			}
			else
			{
				moveUnit(u, getRegionLocation(attackPoint, 100, 100));
			}
		}
		else
		{
			moveUnit(u, getRegionLocation(new Location(0, 0, 0), w.getWidth(), w.getDepth()));
		}
	}
	/**
	 * gives the factories commands
	 * @param u the factory
	 */
	private void orderFactory(Unit u)
	{
		if(workers < maxWorkers)
		{
			buildAt("worker", u, u.getLocation());
			workers++;
		}
		else if(lightTanks < maxLightTanks)
		{
			buildAt("light tank", u, u.getLocation());
			lightTanks++;
		}
	}
	/**
	 * gives the worker commands
	 * @param u the worker
	 * @param buildLocations
	 */
	private void orderWorker(Unit u)
	{
		if(buildLocations.size() > 0)
		{
			int index = (int)(Math.random()*buildLocations.size());
			Location l = getRegionLocation(buildLocations.get(index), 200, 200);
			if(factories < maxFactories && sctpPlants != 0 && refineries != 0)
			{
				buildAt("factory", u, l);
				factories++;
			}
			else if(sctpPlants < maxsctpPlants && refineries != 0)
			{
				buildAt("sctp plant", u, l);
				sctpPlants++;
			}
			else if(refineries < maxRefineries)
			{
				buildAt("refinery", u, l);
				refineries++;
			}
			else
			{
				moveUnit(u, getRegionLocation(buildLocations.get(index), w.getWidth(), w.getDepth()));
			}
		}
		else
		{
			buildAt("sctp plant", u, u.getLocation());
			sctpPlants++;
		}
	}
	/**
	 * determines the game state, counts its units, determines build locations, etc
	 */
	private void determineState()
	{
		workers = 0;
		int lightTanks = 0;
		sctpPlants = 0;
		refineries = 0;
		factories = 0;
		
		ArrayList<Location> al = new ArrayList<Location>();
		LinkedList<Unit> units = getUnits();
		Iterator<Unit> i = units.iterator();
		while(i.hasNext())
		{
			Unit u = i.next();
			if(u.getMovement() == 0)
			{
				al.add(u.getLocation());
			}
			if(u.is("factory"))
			{
				factories++;
			}
			else if(u.is("worker"))
			{
				workers++;
			}
			else if(u.is("light tank"))
			{
				lightTanks++;
			}
			else if(u.is("sctp plant"))
			{
				sctpPlants++;
			}
			else if(u.is("refinery"))
			{
				refineries++;
			}
		}
		buildLocations = al;
		
		attackPoint = null;
		attackingMovingTarget = false;
		Location secondaryAttack = null; //in case it cant find a building
		Iterator<String> it = getEnemyUnits().keySet().iterator();
		while(it.hasNext() && attackPoint == null)
		{
			i = getEnemyUnits().get(it.next()).iterator();
			while(i.hasNext() && attackPoint == null)
			{
				Unit u = i.next();
				if(u.getMovement() == 0)
				{
					attackPoint = u.getLocation();
				}
				else if(secondaryAttack == null)
				{
					secondaryAttack = u.getLocation();
				}
			}
		}
		if(attackPoint == null)
		{
			attackPoint = secondaryAttack;
			if(attackPoint != null)
			{
				attackingMovingTarget = true;
			}
		}
	}
	/**
	 * determines a random location somewhere inside the specified region
	 * @param center the center of the region
	 * @param width the width of the region
	 * @param depth the depth of the region
	 * @return returns a location
	 */
	private Location getRegionLocation(Location center, double width, double depth)
	{
		return new Location(center.x+Math.random()*width-width/2, 0, 
				center.z+Math.random()*depth-depth/2);
	}
}
