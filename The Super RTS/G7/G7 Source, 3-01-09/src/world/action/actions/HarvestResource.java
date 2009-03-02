package world.action.actions;

import world.WorldOverlay;
import world.action.ActionList;
import world.unit.FriendlyUnitMask;
import utilities.Location;
import pathFinder.PathFinder;
import world.World;

/*
 * the actual taking of the resource by the unit before being brought back to a stockpile
 */

public class HarvestResource extends ActionList
{
	public HarvestResource(FriendlyUnitMask fum, WorldOverlay geo, Location location, PathFinder pf, World w)
	{
		super("harvest resource");
		addActionToList(new Move(location, fum, pf, w.getDynamicMap()));
		addActionToList(new LoadResource(fum, geo));
	}
}
