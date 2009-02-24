package world.action.actions;

import world.WorldOverlay;
import world.action.*;
import driver.WorldConstants;
import world.unit.FriendlyUnitMask;
import world.resource.Resource;

/*
 * the actual taking of the resource by the unit before being brought back to a stockpile
 */

public class LoadResource extends Action
{
	WorldOverlay geo;
	FriendlyUnitMask fum;
	public LoadResource(FriendlyUnitMask fum, WorldOverlay geo)
	{
		super("load resource");
		this.fum = fum;
		this.geo = geo;
	}
	public boolean performAction()
	{
		Resource r = geo.getClosestVisibleResource(this, fum);
		if(r != null && r.getLocation().distanceTo(fum.getLocation()) <= WorldConstants.gatherRange)
		{
			fum.getUnit(this).setResource(r);
			geo.removeResource(this, r);
			return true;
		}
		fum.setAction(this, new Idle()); //resource could not be loaded, it is not within load range
		return true;
	}
}
