package ai;

import java.util.HashMap;
import java.util.LinkedList;
import javax.media.opengl.GLAutoDrawable;

import sgEngine.EngineConstants;
import sgEngine.SGEngine;
import sgEngine.userAction.*;
import utilities.Location;
import utilities.Prism;
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
	private SGEngine sge;
	
	public AI(Owner o, World w, SGEngine sge)
	{
		this.w = w;
		this.o = o;
		this.sge = sge;
	}
	/**
	 * tells the unit to move to a given location, does not move if the
	 * units current action is not idle
	 * @param u the unit being ordered
	 * @param target the location that the unit is told to move to
	 */
	protected void moveUnit(Unit u, Location target)
	{
		if(inWorld(target) && u.getAction() instanceof Idle && u.getMovement() > 0)
		{
			u.setAction(new Move(u, new Location(target.x, u.getRestingHeight(), target.z)), false);
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
		if(inWorld(target) && u.getMovement() > 0)
		{
			u.setAction(new Move(u, new Location(target.x, u.getRestingHeight(), target.z)), false);
		}
	}
	/**
	 * tests to see if a location is in the world
	 * @param l the location being tested
	 * @return returns true if the location is in the world, false otherwise
	 */
	private boolean inWorld(Location l)
	{
		/*if(l.x >= -w.getWidth()/2 && l.x <= w.getWidth()/2 && l.z >= -w.getDepth()/2 && l.z <= w.getDepth()/2)
		{
			if(l.y >= 0)
			{
				return true;
			}
		}
		return false;*/
		Prism p = new Prism(EngineConstants.mapCenter, w.getWidth(), w.getHeight(), w.getDepth());
		return p.contains(l);
	}
	/**
	 * sends a unit to build another unit at the specified location if
	 * the location is inside the world bounds and the builder is capable
	 * of building the unit, builder must be idle to build
	 * @param name the name of the unit to be built
	 * @param builder the builder
	 * @param location the location of where the unit is to be built
	 */
	protected void buildAt(String name, Unit builder, Location location)
	{
		if(inWorld(location) && builder.canBuild(name) && builder.getAction() instanceof Idle && 
				o.getPopulation() <= EngineConstants.maxPopulation)
		{
			//Owner o = builder.getOwner();
			builder.setAction(new Build(builder, name, location, sge), false);
		}
	}
	protected HashMap<String, LinkedList<Unit>> getEnemyUnits()
	{
		return w.getUnitEngine().getEnemyUnits(o);
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
