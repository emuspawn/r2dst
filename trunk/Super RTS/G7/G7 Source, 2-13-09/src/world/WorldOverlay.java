package world;

import world.owner.Owner;
//import map.Map;
import world.resource.Resource;
import world.unit.*;
import utilities.Location;
import world.action.Action;
import java.util.LinkedList;
import java.util.ArrayList;

import driver.WorldConstants;

/*
 * given to the class "AI", a restricted GameEngine so the AI wont cheat
 */

public class WorldOverlay
{
	private UnitEngine ue;
	private World w;
	
	public WorldOverlay(World w)
	{
		this.w = w;
		this.ue = w.getUnitEngine();
	}
	public ArrayList<FriendlyUnitMask> getFriendlyUnits(Owner o)
	{
		ArrayList<Unit> u = ue.getFriendlyUnits(o);
		ArrayList<FriendlyUnitMask> fu = new ArrayList<FriendlyUnitMask>(); //friendly units
		for(int i = 0; i < u.size(); i++)
		{
			fu.add(new FriendlyUnitMask(u.get(i)));
		}
		return fu;
	}
	public ArrayList<EnemyUnitMask> getVisibleEnemyUnits(Owner o)
	{
		ArrayList<Unit> u = ue.getUnits();
		//LinkedList<Unit> u = ue.getUnits();
		ArrayList<EnemyUnitMask> eu = new ArrayList<EnemyUnitMask>();
		for(int i = u.size()-1; i >= 0; i--)
		{
			if(!u.get(i).getOwner().getName().equalsIgnoreCase(o.getName()))
			{
				for(int a = u.size()-1; a >= 0; a--)
				{
					if(u.get(a).getOwner().getName().equalsIgnoreCase(o.getName()))
					{
						if(u.get(i).getLocation().distanceTo(u.get(a).getLocation()) <= u.get(a).getViewRange())
						{
							eu.add(new EnemyUnitMask(u.get(i)));
							eu.get(eu.size()-1).getUnit(ue).setLocation(new Location(u.get(i).getLocation().x, u.get(i).getLocation().y));
							break;
						}
					}
				}
			}
		}
		return eu;
	}
	public int getMapWidth()
	{
		return w.getWidth();
	}
	public int getMapHeight()
	{
		return w.getHeight();
	}
	public void removeResource(Object o, Resource r)
	{
		if(o.getClass().getSuperclass() != null)
		{
			if(o.getClass().getSuperclass() == Action.class)
			{
				w.getResources().remove(r);
			}
		}
	}
	public ArrayList<Location> getVisibleResources(Owner o)
	{
		ArrayList<Location> visible = new ArrayList<Location>();
		ArrayList<Resource> r = w.getResources();
		ArrayList<Unit> u = ue.getFriendlyUnits(o);
		for(int i = 0; i < r.size(); i++)
		{
			for(int a = 0; a < u.size(); a++)
			{
				if(r.get(i).getLocation().distanceTo(u.get(a).getLocation()) < u.get(a).getViewRange())
				{
					visible.add(new Location(r.get(i).getLocation().x, r.get(i).getLocation().y));
					break;
				}
			}
		}
		//System.out.println(visible);
		return visible;
	}
	public LinkedList<Location> getVisibleResourcesLL(Owner o)
	{
		//returns a linked list instead of an array list
		LinkedList<Location> visible = new LinkedList<Location>();
		ArrayList<Resource> r = w.getResources();
		ArrayList<Unit> u = ue.getFriendlyUnits(o);
		for(int i = 0; i < r.size(); i++)
		{
			for(int a = 0; a < u.size(); a++)
			{
				if(r.get(i).getLocation().distanceTo(u.get(a).getLocation()) < u.get(a).getViewRange())
				{
					visible.add(new Location(r.get(i).getLocation().x, r.get(i).getLocation().y));
					break;
				}
			}
		}
		return visible;
	}
	public Resource getClosestVisibleResource(Object o, FriendlyUnitMask fum)
	{
		Resource resource = null;
		if(o.getClass().getSuperclass() != null)
		{
			if(o.getClass().getSuperclass() == Action.class)
			{
				ArrayList<Resource> r = w.getResources();
				double sdist = 9999999;
				for(int i = r.size()-1; i >= 0; i--)
				{
					if(r.get(i).getLocation().distanceTo(fum.getLocation()) < fum.getViewRange())
					{
						if(r.get(i).getLocation().distanceTo(fum.getLocation()) < sdist)
						{
							if(r.get(i).getLocation().distanceTo(fum.getLocation()) < WorldConstants.gatherRange)
							{
								sdist = r.get(i).getLocation().distanceTo(fum.getLocation());
								resource = r.get(i);
							}
						}
					}
				}
			}
		}
		return resource;
	}
}
