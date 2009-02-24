package world.unit;

import ai.AI;
import world.action.Action;

//does the same thing as enemy unit mask, but for friendly units

public class FriendlyUnitMask extends EnemyUnitMask
{
	public FriendlyUnitMask(Unit u)
	{
		super(u);
	}
	public void setAction(Object o, Action a)
	{
		if(o.getClass().getSuperclass() == AI.class || o.getClass().getSuperclass() == Action.class)
		{
			u.a = a;
		}
	}
	public int getViewRange()
	{
		return u.viewRange;
	}
	public void setLabel(String label)
	{
		u.label = label;
	}
	public String getLabel()
	{
		return u.label;
	}
}
