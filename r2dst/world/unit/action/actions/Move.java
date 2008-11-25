package world.unit.action.actions;

import world.unit.action.*;
import utilities.Location;
import pathFinder.*;
import world.unit.FriendlyUnitMask;
import java.awt.Rectangle;
import java.util.ArrayList;
import utilities.MoverseV3;

public class Move extends Action
{
	Location destination;
	FriendlyUnitMask u;
	PathFinder pf;
	Path p;
	ArrayList<Rectangle> bounds;
	Location next; //the point the unit is currently moving to
	
	public Move(Location destination, FriendlyUnitMask u, PathFinder pf, ArrayList<Rectangle> bounds)
	{
		super("move");
		this.destination = destination;
		this.u = u;
		this.pf = pf;
		this.bounds = bounds;
	}
	public boolean performAction()
	{
		if(p == null)
		{
			p = pf.findPath(destination, u.getLocation(), bounds);
			next = p.getNextLocation();
		}
		try
		{
			u.getUnit(this).setLocation(MoverseV3.getNewLocation(u.getLocation(), next, u.getMovement()));
		}
		catch(NullPointerException e)
		{
			//for some reason after units die u.getLocation() is null
			return true;
		}
		if(u.getLocation().x == next.x && u.getLocation().y == next.y)
		{
			next = p.getNextLocation();
		}
		if(next == null)
		{
			//u.setAction(new Idle());
			return true;
		}
		return false;
	}
}
