package world.unit;

import java.util.ArrayList;
import world.action.actions.Idle;
import world.owner.Owner;
import world.dynamicMap.DynamicMap;
import utilities.Location;

/**
 * the class that regulates all unit activities
 * @author Jack
 *
 */
public class UnitEngine
{
	ArrayList<Unit> u = new ArrayList<Unit>();
	DynamicMap dm;
	
	public UnitEngine(DynamicMap dm)
	{
		this.dm = dm;
	}
	public void registerUnit(Unit unit)
	{
		u.add(unit);
	}
	public void performUnitFunctions()
	{
		Location start;
		Location end;
		for(int i = u.size()-1; i >= 0; i--)
		{
			start = u.get(i).getLocation();
			performAction(i);
			end = u.get(i).getLocation();
			dm.adjustElement(u.get(i), start, end);
			checkForDeath(i);
		}
	}
	private void performAction(int i)
	{
		boolean done;
		if(u.get(i).getAction() != null)
		{
			done = u.get(i).getAction().performAction();
			if(done)
			{
				u.get(i).setAction(new Idle());
			}
		}
	}
	/**
	 * checks to see if the unit has died, if it has it sets the unit's
	 * status to dead so that all references to it will be removed from
	 * all other
	 * @param i the index in unit list of the unit being checked
	 */
	private void checkForDeath(int i)
	{
		if(u.get(i).life <= 0)
		{
			Owner o = u.get(i).getOwner();
			o.setCurrentUnitMax(this, o.getCurrentUnitMax()-u.get(i).getPopulationAugment());
			o.setUnitCount(this, o.getUnitCount()-u.get(i).getPopulationValue());
			
			u.get(i).setDead();
			
			dm.removeElement(u.remove(i));
		}
	}
	/**
	 * gets all units under the control of the owner
	 * @param o the owner of the units
	 * @return returns list of all friendly units
	 */
	public ArrayList<Unit> getFriendlyUnits(Owner o)
	{
		ArrayList<Unit> temp = new ArrayList<Unit>();
		for(int i = 0; i < u.size(); i++)
		{
			if(u.get(i).getOwner().getName().equalsIgnoreCase(o.getName()))
			{
				temp.add(u.get(i));
			}
		}
		return temp;
	}
	/**
	 * gets a list of all game units
	 * @return returns a list of all units
	 */
	public ArrayList<Unit> getUnits()
	{
		return u;
	}
}
