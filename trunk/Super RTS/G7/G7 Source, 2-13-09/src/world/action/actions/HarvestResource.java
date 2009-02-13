package world.action.actions;

import world.WorldOverlay;
import world.action.ActionList;
import world.unit.FriendlyUnitMask;
import utilities.Location;
import pathFinder.PathFinder;

/*
 * the actual taking of the resource by the unit before being brought back to a stockpile
 */

public class HarvestResource extends ActionList
{
	public HarvestResource(FriendlyUnitMask fum, WorldOverlay geo, Location location, PathFinder pf)
	{
		super("harvest resource");
		addActionToList(new Move(location, fum, pf, null));
		addActionToList(new LoadResource(fum, geo));
	}
}
