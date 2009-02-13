package world.unit;

import java.util.ArrayList;
import world.action.actions.Idle;
import world.owner.Owner;
import world.dynamicMap.DynamicMap;
import utilities.Location;

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
			
			/*Location l = u.get(i).getLocation();
			u.get(i).setLocation(new Location(l.x+3, l.y+3));*/
		}
		//System.out.println("units = "+u.size());
	}
	private void performAction(int i)
	{
		boolean done;
		if(u.get(i).a != null)
		{
			//done = u.get(i).a.performAction();
			done = u.get(i).getAction().performAction();
			if(done)
			{
				u.get(i).a = new Idle();
			}
		}
	}
	private void checkForDeath(int i)
	{
		if(u.get(i).life <= 0)
		{
			Owner o = u.get(i).getOwner();
			o.setCurrentUnitMax(this, o.getCurrentUnitMax()-u.get(i).getPopulationAugment());
			o.setUnitCount(this, o.getUnitCount()-u.get(i).getPopulationValue());
			dm.removeElement(u.remove(i));
		}
	}
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
	public ArrayList<Unit> getUnits()
	{
		return u;
	}
}
