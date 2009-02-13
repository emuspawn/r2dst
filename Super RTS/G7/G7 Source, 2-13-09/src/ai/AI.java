package ai;

import world.unit.*;
import factory.*;
import world.action.*;
import driver.WorldConstants;
import world.BuildEngineOverlay;
import world.WorldOverlay;
import pathFinder.PathFinder;
import world.action.actions.*;
import java.util.ArrayList;
import world.owner.Owner;
import utilities.Location;

public abstract class AI
{
	protected WorldOverlay wo;
	protected PathFinder pf;
	protected Owner o;
	private BuildEngineOverlay beo;
	
	public AI(Owner o, WorldOverlay wo, BuildEngineOverlay beo, PathFinder pf)
	{
		this.o = o;
		this.wo = wo;
		this.beo = beo;
		this.pf = pf;
	}
	protected int gatherClosestResource(FriendlyUnitMask u, ArrayList<Location> l)
	{
		/*
		 * gathers resource at the closest location of the array list given, returns
		 * the index of the location it went to in order to retrieve the resource
		 */
		double distance = 99999999;
		int index = -1;
		if(u.getAction().getName().equalsIgnoreCase("idle"))
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
				u.setAction(this, new GatherResource(u.getOwner(), wo.getFriendlyUnits(u.getOwner()), u, l.get(index), wo, pf));
			}
		}
		return index;
	}
	protected boolean moveUnit(FriendlyUnitMask u, Location destination)
	{
		//returns true if the unit was successfully given the order to move
		if(destination.x >= 0 && destination.x <= wo.getMapWidth() && destination.y >= 0 && destination.y <= wo.getMapHeight())
		{
			if(u.getAction().getName().equalsIgnoreCase("idle"))
			{
				u.setAction(this, new Move(destination, u, pf, null));
				return true;
			}
		}
		return false;
	}
	protected void exexuteActionList(ActionList actionList, FriendlyUnitMask unit)
	{
		if(unit.getAction().getName().equalsIgnoreCase("idle"))
		{
			unit.setAction(this, actionList);
		}
	}
	protected boolean build(String name, FriendlyUnitMask builder)
	{
		return buildAt(name, builder, builder.getLocation());
	}
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
								//al.addActionToList(new Build(beo, builder, builder.getLocation(), u));
								al.addActionToList(new Build(beo, builder, u));
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
