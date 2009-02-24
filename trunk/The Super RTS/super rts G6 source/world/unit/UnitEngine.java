package world.unit;

import java.util.ArrayList;
import java.util.LinkedList;
import world.unit.action.actions.Idle;
import owner.Owner;

public class UnitEngine
{
	//ArrayList<Unit> u = new ArrayList<Unit>();
	LinkedList<Unit> u = new LinkedList<Unit>();
	
	public void performUnitFunctions()
	{
		boolean done;
		for(int i = 0; i < u.size(); i++)
		{
			if(u.get(i).a != null)
			{
				//System.out.println(u.get(i).a.getName());
				done = u.get(i).a.performAction();
				if(done)
				{
					u.get(i).a = new Idle();
				}
				//System.out.println(u.get(i).getLocation().toString()+", unit = "+u.get(i).name+", owner = "+u.get(i).owner.getName()+", movement = "+u.get(i).getMovement());
			}
			if(u.get(i).life <= 0)
			{
				Owner o = u.get(i).owner;
				o.setCurrentUnitMax(this, o.getCurrentUnitMax()-u.get(i).getPopulationAugment());
				o.setUnitCount(this, o.getUnitCount()-u.get(i).getPopulationValue());
				u.remove(i);
				//System.out.println("unit killed");
			}
		}
		//System.out.println("-----");
	}
	public ArrayList<Unit> getFriendlyUnits(Owner o)
	{
		ArrayList<Unit> temp = new ArrayList<Unit>();
		for(int i = 0; i < u.size(); i++)
		{
			if(u.get(i).owner.getName().equalsIgnoreCase(o.getName()))
			{
				temp.add(u.get(i));
			}
		}
		return temp;
	}
	//public ArrayList<Unit> getUnits()
	public LinkedList<Unit> getUnits()
	{
		return u;
	}
}
