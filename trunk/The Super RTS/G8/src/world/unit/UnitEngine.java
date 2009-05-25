package world.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import dynamicMap3D.DynamicMap3D;
import sgEngine.EngineConstants;
import world.World;
import world.action.actions.*;
import world.owner.Owner;
import world.shot.ShotEngine;

/**
 * manages the game units
 * @author Jack
 *
 */
public class UnitEngine
{
	/**
	 * key is the name of the owner, value is a linked list of units belonging to that owner
	 * sorts the game units
	 */
	HashMap<String, LinkedList<Unit>> units = new HashMap<String, LinkedList<Unit>>();
	int totalUnits = 0;
	ArrayList<Unit> unitQueue = new ArrayList<Unit>();
	DynamicMap3D dm3d;
	
	World w;
	ShotEngine se;
	
	int idCount = 0; //for determining ids
	
	public UnitEngine(World w, ShotEngine se)
	{
		this.w = w;
		this.se = se;
		//dm3d = new DynamicMap3D(new Location(0, w.getHeight()/2, 0), w.getWidth(), w.getHeight(), w.getDepth());
		dm3d = new DynamicMap3D(EngineConstants.mapCenter, w.getWidth(), w.getHeight(), w.getDepth());
		//dm3d.setPartitionSize(100);
	}
	public void performUnitFunctions()
	{
		Iterator<String> ki = units.keySet().iterator();
		while(ki.hasNext())
		{
			Iterator<Unit> i = units.get(ki.next()).iterator();
			while(i.hasNext())
			{
				Unit u = i.next();
				updateUnit(u);
				if(u.isDead())
				{
					dm3d.removeElement(u, u.getID());
					u.getAction().cancelAction();
					i.remove();
					totalUnits--;
				}
			}
		}
		addQueuedUnitsToMainList();
		determinePopulations();
	}
	/**
	 * determines the populations for each owner
	 */
	private void determinePopulations()
	{
		Iterator<String> ki = units.keySet().iterator();
		while(ki.hasNext())
		{
			LinkedList<Unit> ll = units.get(ki.next());
			if(ll.size() > 0)
			{
				Owner o = ll.getFirst().getOwner();
				o.setPopulation(ll.size());
			}
		}
	}
	/**
	 * adds queued units to the main list
	 */
	private void addQueuedUnitsToMainList()
	{
		for(int a = unitQueue.size()-1; a >= 0; a--)
		{
			if(units.get(unitQueue.get(a).getOwner().getName()) != null)
			{
				units.get(unitQueue.get(a).getOwner().getName()).add(unitQueue.get(a));
			}
			else
			{
				units.put(unitQueue.get(a).getOwner().getName(), new LinkedList<Unit>());
				units.get(unitQueue.get(a).getOwner().getName()).add(unitQueue.get(a));
			}
			totalUnits++;
			unitQueue.remove(a);
		}
	}
	/**
	 * updates the passed unit, makes the unit perform its action,
	 * sets a unit to dead if necesary
	 * @param u the unit being updated
	 */
	private void updateUnit(Unit u)
	{
		//Location oldLocation = u.getLocation();
		//boolean done = u.getAction().performAction();
		//dm3d.adjustElement(u, oldLocation, u.getLocation(), u.getID());
		
		dm3d.removeElement(u, u.getID());
		boolean done = u.getAction().performAction();
		dm3d.addElement(u, u.getID());
		
		if(done)
		{
			u.setAction(new Idle(), true);
		}
		if(u.getLife() <= 0)
		{
			u.setDead();
		}
		else
		{
			if(u.getWeapon().getRange() > 0)
			{
				u.getWeapon().updateWeapon();
				u.getWeapon().fireWeapon(u.getLocation(), se, dm3d, u.getOwner());
			}
			Owner o = u.getOwner();
			o.setEnergy(o.getEnergy()-u.getEnergyDrain());
			o.setMetal(o.getMetal()-u.getMetalDrain());
		}
		//System.out.println(u.getOwner().getName());
	}
	/**
	 * gets the total units in game
	 * @return returns the total units
	 */
	public int getTotalUnits()
	{
		return totalUnits;
	}
	/**
	 * gets the unit map, necesary for calculating shot collisions, etc
	 * @return returns the unit map
	 */
	public DynamicMap3D getUnitMap()
	{
		return dm3d;
	}
	/**
	 * adds a unit to the queue to be added to the main unit list
	 * @param unit
	 */
	public void registerUnit(Unit unit)
	{
		unit.setID(idCount);
		idCount++;
		unitQueue.add(unit);
		dm3d.addElement(unit, unit.getID());
	}
	/**
	 * gets the units controlled by a given owner
	 * @param o the owner of the units
	 * @return returns the controlled units
	 */
	public LinkedList<Unit> getUnits(Owner o)
	{
		return units.get(o.getName());
	}
	/**
	 * like the getUnits(Owner o) method, gets a hash map containing
	 * all the units of all owners, key=owner name value=linked list
	 * of units
	 * @return returns the unit has hmap
	 */
	public HashMap<String, LinkedList<Unit>> getUnits()
	{
		return units;
	}
	/**
	 * gets all the games units
	 * @return returns all game units
	 */
	public LinkedList<Unit> getAllUnits()
	{
		LinkedList<Unit> ll = new LinkedList<Unit>();
		Iterator<String> i = units.keySet().iterator();
		while(i.hasNext())
		{
			ll.addAll(units.get(i.next()));
		}
		return ll;
	}
	/**
	 * returns a hash map representing all enemy units, keys
	 * are the names of the owners of the enemy units, values
	 * are linked list representing those units
	 * @param o the owner
	 * @return returns units that are enemies to the passed owner
	 */
	public HashMap<String, LinkedList<Unit>> getEnemyUnits(Owner o)
	{
		HashMap<String, LinkedList<Unit>> hm = new HashMap<String, LinkedList<Unit>>(units);
		hm.remove(o.getName());
		return hm;
	}
}
