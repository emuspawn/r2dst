package world.action.actions;

import utilities.Location;
import world.World;
import world.action.ActionList;
import java.util.ArrayList;
import world.unit.FriendlyUnitMask;
import pathFinder.PathFinder;
import world.owner.Owner;

/*
 * the action for depositing the resource at an hq, moves the unit to the closest hq then
 * deposits the resource, if the building is not in range when done moving (ex. it died)
 * then it sets the action of the unit to a new DepositResource and it again searchs for the
 * closest hq to deposit resources and tries again
 */

public class DepositResource extends ActionList
{
	ArrayList<FriendlyUnitMask> fu;
	FriendlyUnitMask fum;
	
	public DepositResource(Owner o, ArrayList<FriendlyUnitMask> fu, FriendlyUnitMask fum, PathFinder pf, World w)
	{
		super("deposit resource");
		this.fu = fu;
		this.fum = fum;
		Location closestBuilding = null;
		int buildingIndex = 0;
		for(int i = 0; i < fu.size(); i++)
		{
			if(fu.get(i).getName().equalsIgnoreCase("hq"))
			{
				if(closestBuilding == null || fum.getLocation().distanceTo(fu.get(i).getLocation())
						< fum.getLocation().distanceTo(closestBuilding))
				{
					closestBuilding = fu.get(i).getLocation();
					buildingIndex = i;
				}
			}
		}
		addActionToList(new Move(closestBuilding, fum, pf, w.getDynamicMap()));
		addActionToList(new UnloadResource(o, fum, fu.get(buildingIndex), fu, pf, w));
	}
}
