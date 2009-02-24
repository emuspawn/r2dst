package world.unit.action.actions;

import world.unit.action.ActionList;
import world.unit.FriendlyUnitMask;
import driver.GameEngineOverlay;
import driver.GameOverlay;
import utilities.Location;
import pathFinder.PathFinder;

/*
 * the actual taking of the resource by the unit before being brought back to a stockpile
 */

public class HarvestResource extends ActionList
{
	public HarvestResource(FriendlyUnitMask fum, GameOverlay go, Location location, PathFinder pf)
	{
		super("harvest resource");
		addActionToList(new Move(location, fum, pf, null));
		addActionToList(new LoadResource(fum, go));
	}
}
