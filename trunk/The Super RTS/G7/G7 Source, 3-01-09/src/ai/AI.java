package ai;

import world.unit.*;
import factory.*;
import world.action.*;
import world.WorldConstants;
import world.WorldOverlay;
import pathFinder.PathFinder;
import world.action.actions.*;
import java.util.ArrayList;
import world.owner.Owner;
import utilities.Location;
import world.World;

public abstract class AI
{
	protected WorldOverlay wo;
	protected PathFinder pf;
	protected Owner o;
	
	private World w;
	
	public AI(Owner o, WorldOverlay wo, PathFinder pf)
	{
		this.o = o;
		this.wo = wo;
		this.pf = pf;
	}
	/**
	 * sends a unit to gather resource at the closest location of the 
	 * array list given
	 * @param u the unit being sent to gather the resource
	 * @param l a list of locations where resources reside
	 * @return returns the index of the closest resource in the list
	 */
	protected int gatherClosestResource(FriendlyUnitMask u, ArrayList<Location> l)
	{
		/*
		 * gathers resource at the closest location of the array list given, returns
		 * the index of the location it went to in order to retrieve the resource
		 */
		double distance = 99999999;
		int index = -1;
		if(u.getAction().getName().equalsIgnoreCase("idle") && u.isGatherer())
		{
			for(int i = 0; i < l.size(); i++)
			{
				if(l.get(i).distanceTo(u.getLocation()) < distance)
				{
					distance = l.get(i).distanceTo(u.getLocation());
					index = i;
				}
			}
			if(index != -1)
			{
				u.setAction(this, new GatherResource(u.getOwner(), wo.getFriendlyUnits(u.getOwner()), u, l.get(index), wo, pf, w));
			}
		}
		return index;
	}
	/**
	 * sets the units action to Move if possible, must be
	 * idle to take a new action, must have movement > 0
	 * to move
	 * @param u the unit the order is going to
	 * @param destination where the unit is moving to
	 * @return returns whether or not the unit was given the order to move
	 */
	protected boolean moveUnit(FriendlyUnitMask u, Location destination)
	{
		//returns true if the unit was successfully given the order to move
		if(destination.x >= 0 && destination.x <= wo.getMapWidth() && destination.y >= 0 && destination.y <= wo.getMapHeight())
		{
			if(u.getAction().getName().equalsIgnoreCase("idle") && u.getMovement() > 0)
			{
				u.setAction(this, new Move(destination, u, pf, w.getDynamicMap()));
				return true;
			}
		}
		return false;
	}
	public void setWorld(World w)
	{
		this.w = w;
	}
	/**
	 * orders a unit to build another unit
	 * @param name the name of the unit to be built
	 * @param builder the builder
	 * @return returns true if order is given successfully
	 */
	protected boolean build(String name, FriendlyUnitMask builder)
	{
		return buildAt(name, builder, builder.getLocation());
	}
	/**
	 * orders a unit to build another unit at a certain location
	 * @param name name the name of the unit to be built
	 * @param builder builder the builder
	 * @param l the location where the unit will be built
	 * @return returns true if order is given successfully
	 */
	protected boolean buildAt(String name, FriendlyUnitMask builder, Location l)
	{
		if(l.x >= 0 && l.x <= wo.getMapWidth() && l.y >= 0 && l.y <= wo.getMapHeight())
		{
			if(builder.getMovement() > 0 || (l.x == builder.getLocation().x && l.y == builder.getLocation().y))
			{
				if(builder.getAction().getName().equalsIgnoreCase("idle"))
				{
					Unit u = UnitFactory.makeUnit(o, name, l);
					int pop = o.getUnitCount()+u.getPopulationValue()-u.getPopulationAugment();
					if(builder.isBuilder() && pop <= o.getCurrentUnitMax() && pop <= WorldConstants.maxUnitCount)
					{
						if(o.getMass() >= u.getCost())
						{
							if(builder.getBuildTree().canBuildUnit(name))
							{
								u.getOwner().lowerMass(u.getCost());
								ActionList al = new ActionList("build at");
								al.addActionToList(new Move(l, builder, pf, null));
								al.addActionToList(new Build(w.getBuildEngine(), builder, u));
								builder.setAction(this, al);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	public abstract void performAIFunctions();
}
