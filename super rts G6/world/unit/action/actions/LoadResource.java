package world.unit.action.actions;

import world.unit.action.*;
import driver.GameConstants;
import driver.GameOverlay;
import world.unit.FriendlyUnitMask;
import driver.GameEngineOverlay;
import world.resource.Resource;

/*
 * the actual taking of the resource by the unit before being brought back to a stockpile
 */

public class LoadResource extends Action
{
	GameOverlay geo;
	FriendlyUnitMask fum;
	public LoadResource(FriendlyUnitMask fum, GameOverlay go)
	{
		super("load resource");
		this.fum = fum;
		this.geo = go;
	}
	public boolean performAction()
	{
		Resource r = geo.getClosestVisibleResource(this, fum);
		if(r != null && r.getLocation().distanceTo(fum.getLocation()) <= GameConstants.gatherRange)
		{
			fum.getUnit(this).setResource(r);
			geo.removeResource(this, r);
			return true;
		}
		fum.setAction(this, new Idle()); //resource could not be loaded, it is not within load range
		return true;
	}
}
