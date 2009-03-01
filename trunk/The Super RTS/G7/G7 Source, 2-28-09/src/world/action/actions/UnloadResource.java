package world.action.actions;

import world.WorldConstants;
import world.action.Action;
import world.unit.FriendlyUnitMask;
import world.owner.Owner;
import java.util.ArrayList;
import pathFinder.PathFinder;
import world.World;

/*
 * the actual drop off of a gathered resource, sets action of unit to DepositResource if unable to
 * unload resources
 */

public final class UnloadResource extends Action
{
	FriendlyUnitMask unit;
	FriendlyUnitMask building;
	Owner o;
	ArrayList<FriendlyUnitMask> fu; //friendly units, needed to set action to DepositResource
	PathFinder pf;
	World w;
	
	public UnloadResource(Owner o, FriendlyUnitMask unit, FriendlyUnitMask building, ArrayList<FriendlyUnitMask> fu, PathFinder pf, World w)
	{
		super("unload resource");
		this.unit = unit;
		this.building = building;
		this.o = o;
		this.fu = fu;
		this.pf = pf;
		this.w = w;
	}
	public boolean performAction()
	{
		if(unit.getLocation().distanceTo(building.getLocation()) <= WorldConstants.unloadRange)
		{
			o.setMass(o.getMass()+unit.getUnit(this).getMassHolding());
			//o.addResource(unit.getUnit(this).getResource());
			//unit.getUnit(this).setResource(null);
			return true;
			//unit.setAction(new Idle());
		}
		else
		{
			unit.setAction(this, new DepositResource(o, fu, unit, pf, w));
			return false;
		}
	}
}
