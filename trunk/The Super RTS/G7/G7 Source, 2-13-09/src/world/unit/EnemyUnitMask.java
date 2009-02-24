package world.unit;

import world.action.Action;
import world.owner.Owner;
import utilities.Location;
import java.awt.Rectangle;

/*
 * a mask for a unit, given to the AI through the UnitEngineOverlay so the AI cannot cheat and
 * can only perform functions that a player would be able to perform
 */

public class EnemyUnitMask
{
	protected Unit u;
	
	public EnemyUnitMask(Unit u)
	{
		this.u = u;
	}
	public boolean is(String type)
	{
		return u.is(type);
	}
	public int getLife()
	{
		return u.life;
	}
	public Action getAction()
	{
		return u.a;
	}
	public String getName()
	{
		return u.getName();
	}
	public BuildTree getBuildTree()
	{
		return u.bt;
	}
	public Unit getUnit(Object o)
	{
		if(o.getClass().getSuperclass() != null)
		{
			if(o.getClass().getSuperclass() == Action.class)
			{
				return u;
			}
		}
		if(o.getClass() == UnitEngine.class)
		{
			return u;
		}
		return null;
	}
	public Location getLocation()
	{
		return new Location(u.getLocation().x, u.getLocation().y);
	}
	public Rectangle getBounds()
	{
		return u.getBounds();
	}
	public boolean isBuilding()
	{
		return u.isBuilding;
	}
	public int getMovement()
	{
		return u.getMovement();
	}
	public Owner getOwner()
	{
		return u.getOwner();
	}
	public boolean isBuilder()
	{
		return u.isBuilder;
	}
	public boolean isGatherer()
	{
		return u.isGatherer;
	}
}
