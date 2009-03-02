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
			u.setAction(a);
		}
	}
	public int getViewRange()
	{
		return u.viewRange;
	}
	/**
	 * used to set descriptions of units so AIs can
	 * better group them, does not affect actual gameplay
	 * at all
	 * @param description the new description of the unit
	 */
	public void setDescription(String description)
	{
		u.description = description;
	}
	public String getDescription()
	{
		return u.description;
	}
}
