package world.action.actions;

import world.WorldOverlay;
import world.action.*;
import world.owner.Owner;
import world.unit.FriendlyUnitMask;
import utilities.Location;
import pathFinder.PathFinder;
import java.util.ArrayList;

/*
 * the action list for commanding a gatherer to go and harvest a given resource
 */

public class GatherResource extends ActionList
{
	public GatherResource(Owner o, ArrayList<FriendlyUnitMask> fu, FriendlyUnitMask fum, Location location, WorldOverlay geo, PathFinder pf)
	{
		//location is the location the unit is going to before gathering closest in range resource
		super("gather resource");
		addActionToList(new HarvestResource(fum, geo, location, pf));
		addActionToList(new DepositResource(o, fu, fum, pf));
	}
}