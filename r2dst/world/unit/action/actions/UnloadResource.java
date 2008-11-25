package world.unit.action.actions;

import world.unit.action.Action;
import world.unit.FriendlyUnitMask;
import owner.Owner;
import driver.GameConstants;
import java.util.ArrayList;
import pathFinder.PathFinder;

/*
 * the actual drop off of a gathered resource, sets action of unit to DepositResource if unable to
 * unload resources
 */

public class UnloadResource extends Action
{
	FriendlyUnitMask unit;
	FriendlyUnitMask building;
	Owner o;
	ArrayList<FriendlyUnitMask> fu; //friendly units, needed to set action to DepositResource
	PathFinder pf;
	
	public UnloadResource(Owner o, FriendlyUnitMask unit, FriendlyUnitMask building, ArrayList<FriendlyUnitMask> fu, PathFinder pf)
	{
		super("unload resource");
		this.unit = unit;
		this.building = building;
		this.o = o;
		this.fu = fu;
		this.pf = pf;
	}
	public boolean performAction()
	{
		if(unit.getUnit(this).getResource() == null)
		{
			System.out.println("unit return nothing");
			return true;
		}
		if(unit.getLocation().distanceTo(building.getLocation()) <= GameConstants.unloadRange)
		{
			o.addResource(unit.getUnit(this).getResource());
			unit.getUnit(this).setResource(null);
			return true;
			//unit.setAction(new Idle());
		}
		else
		{
			unit.setAction(this, new DepositResource(o, fu, unit, pf));
			return false;
		}
	}
}
