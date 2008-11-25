package ai;

import world.unit.*;
import world.unit.action.ActionList;
import driver.GameConstants;
import world.BuildEngineOverlay;
import pathFinder.PathFinder;
import world.unit.action.actions.*;
import world.unit.building.buildings.*;
import world.unit.building.buildings.DefenseTurret;
import world.unit.units.*;
import java.util.ArrayList;
import driver.GameEngineOverlay;
import owner.Owner;
import utilities.Location;

public abstract class AI
{
	protected GameEngineOverlay geo;
	protected PathFinder pf;
	protected Owner o;
	private BuildEngineOverlay beo;
	
	public AI(Owner o, GameEngineOverlay geo, BuildEngineOverlay beo, PathFinder pf)
	{
		this.o = o;
		this.geo = geo;
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
				u.setAction(this, new GatherResource(u.getOwner(), geo.getFriendlyUnits(u.getOwner()), u, l.get(index), geo, pf));
			}
		}
		return index;
	}
	protected boolean moveUnit(FriendlyUnitMask u, Location destination)
	{
		//returns true if the unit was successfully given the order to move
		if(destination.x >= 0 && destination.x <= geo.getMapWidth() && destination.y >= 0 && destination.y <= geo.getMapHeight())
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
		if(l.x >= 0 && l.x <= geo.getMapWidth() && l.y >= 0 && l.y <= geo.getMapHeight())
		{
			if(builder.getAction().getName().equalsIgnoreCase("idle"))
			{
				Unit u = getUnit(o, name, l);
				int pop = o.getUnitCount()+u.getPopulationValue()-u.getPopulationAugment();
				if(builder.isBuilder() && pop <= o.getCurrentUnitMax() && pop <= GameConstants.maxUnitCount)
				{
					if(o.getResourceCount("green circle") >= u.getCost())
					{
						if(builder.getBuildTree().canBuildUnit(name))
						{
							u.getOwner().lowerResouceCount("green circle", u.getCost());
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
		return false;
	}
	private Unit getUnit(Owner owner, String name, Location l)
	{
		if(name.equalsIgnoreCase("worker1") || name.equalsIgnoreCase("worker"))
		{
			return new Worker(owner, l);
		}
		else if(name.equalsIgnoreCase("fighter1") || name.equalsIgnoreCase("fighter"))
		{
			return new Fighter(owner, l);
		}
		else if(name.equalsIgnoreCase("hq1") || name.equalsIgnoreCase("hq"))
		{
			return new HQ(owner, l);
		}
		else if(name.equalsIgnoreCase("barracks1") || name.equalsIgnoreCase("barracks"))
		{
			return new Barracks(owner, l);
		}
		else if(name.equalsIgnoreCase("defense turret"))
		{
			return new DefenseTurret(owner, l);
		}
		else if(name.equalsIgnoreCase("engineer"))
		{
			return new Engineer(owner, l);
		}
		else if(name.equalsIgnoreCase("factory"))
		{
			return new Factory(owner, l);
		}
		else if(name.equalsIgnoreCase("harvester"))
		{
			return new Harvester(owner, l);
		}
		return null;
	}
	public abstract void performAIFunctions();
}
