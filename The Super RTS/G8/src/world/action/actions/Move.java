package world.action.actions;

import utilities.Location;
import utilities.MoverseV4;
import world.action.Action;
import world.unit.Unit;

/**
 * moves an elements
 * @author Jack
 *
 */
public class Move extends Action
{
	Unit u;
	Location destination;
	
	/**
	 * creates a new move action
	 * @param u the unit being moved
	 * @param destination the destination of the unit
	 */
	public Move(Unit u, Location destination)
	{
		super("move");
		this.u = u;
		this.destination = destination;
	}
	public boolean performAction()
	{	Location fLocation = MoverseV4.getNewLocation(u.getLocation(), destination, u.getMovement());
		u.setLocation(fLocation);
		if(fLocation.compareTo(destination) == 0)
		{
			return true;
		}
		return false;
	}
}
