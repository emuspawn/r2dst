package ai;

import java.util.LinkedList;
import javax.media.opengl.GLAutoDrawable;
import sgEngine.userAction.*;
import utilities.Location;
import world.World;
import world.action.actions.*;
import world.owner.Owner;
import world.unit.Unit;

/**
 * defines the base methods for an ai, each ai also handels the user actions
 * of their owners (computer ais dont need to use these methods) including
 * mouse clicks, drags, etc
 * @author Jack
 *
 */
public abstract class AI
{
	protected World w;
	protected Owner o;
	
	public AI(Owner o, World w)
	{
		this.w = w;
		this.o = o;
	}
	/**
	 * tells the unit to move to a given location, does not move if the
	 * units current action is not idle
	 * @param u the unit being ordered
	 * @param target the location that the unit is told to move to
	 */
	protected void moveUnit(Unit u, Location target)
	{
		if(inWorld(target) && u.getAction() instanceof Idle)
		{
			u.setAction(new Move(u, target));
		}
	}
	/**
	 * tells the unit to move to a given location, moves even if the unit
	 * is not idle
	 * @param u the unit being ordered
	 * @param target the location that the unit is told to move to
	 */
	protected void forceMoveUnit(Unit u, Location target)
	{
		if(inWorld(target))
		{
			u.setAction(new Move(u, target));
		}
	}
	/**
	 * tests to see if a location is in the world
	 * @param l the location being tested
	 * @return returns true if the location is in the world, false otherwise
	 */
	private boolean inWorld(Location l)
	{
		if(l.x >= -w.getWidth()/2 && l.x <= w.getWidth()/2 && l.z >= -w.getDepth()/2 && l.z <= w.getDepth()/2)
		{
			if(l.y >= 0)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * the main AI method, called once by the SGEngine every iteration
	 * of the main thread
	 */
	public abstract void performAIFunctions();
	/**
	 * draws a UI for this ai
	 * @param d
	 */
	public abstract void drawUI(GLAutoDrawable d);
	public abstract void interpretMouseClick(MouseClick ma);
	public abstract void interpretKeyPress(KeyPress kp);
	public abstract void interpretKeyRelease(KeyRelease kr);
	/**
	 * gets the units under the control of this ai, a
	 * convenience method
	 * @return returns controlled units
	 */
	protected LinkedList<Unit> getUnits()
	{
		LinkedList<Unit> ll = w.getUnitEngine().getUnits(o);
		if(ll == null)
		{
			ll = new LinkedList<Unit>();
		}
		return ll;
	}
}
