package world.action.actions;

import world.WorldConstants;
import world.action.*;
import utilities.Location;
import pathFinder.*;
import world.unit.FriendlyUnitMask;
import java.awt.Rectangle;
import utilities.MoverseV3;
import world.dynamicMap.DynamicMap;

/**
 * moves elements
 * @author Jack
 *
 */
public final class Move extends Action
{
	Location destination;
	FriendlyUnitMask u;
	PathFinder pf;
	Path p;
	Location next; //the point the unit is currently moving to
	
	DynamicMap dm;
	
	public Move(Location destination, FriendlyUnitMask u, PathFinder pf, DynamicMap dm)
	{
		super("move");
		this.destination = destination;
		this.u = u;
		this.pf = pf;
		
		this.dm = dm;
	}
	public boolean performAction()
	{
		if(p == null)
		{
			p = pf.findPath(destination, u.getLocation(), null);
			next = p.getNextLocation();
		}
		try
		{
			//System.out.println(dm == null);
			Location l = MoverseV3.getNewLocation(u.getLocation(), next, u.getMovement());
			Rectangle r = new Rectangle((int)l.x, (int)l.y, u.getUnit(this).getWidth(), u.getUnit(this).getHeight());
			if(!WorldConstants.checkCollisions || !dm.checkIntersection(r))
			{
				u.getUnit(this).setLocation(l);
				//System.out.prin
			}
			//System.out.println("here 4");
		}
		catch(NullPointerException e)
		{
			return true;
		}
		if(u.getLocation().x == next.x && u.getLocation().y == next.y)
		{
			next = p.getNextLocation();
		}
		if(next == null)
		{
			return true;
		}
		return false;
	}
}
