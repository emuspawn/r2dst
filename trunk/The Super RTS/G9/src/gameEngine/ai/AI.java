package gameEngine.ai;

import java.util.LinkedList;

import gameEngine.world.World;
import gameEngine.world.action.actions.*;
import gameEngine.world.owner.Owner;
import gameEngine.world.unit.Unit;

import javax.media.opengl.GL;

import utilities.Location;

/**
 * defines the base methods for an ai, each ai also handles the user actions
 * of its owner (computer ais dont need to use these methods) including
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
	 * gets the units controlled by this ai
	 * @return returns a list of all controlled units
	 */
	public LinkedList<Unit> getUnits()
	{
		return w.getUnitEngine().getUnitList(o);
	}
	/**
	 * orders the passed unit to move to the passed game location,
	 * pushes a move order to the unit action stack
	 * @param u the unit being moved, its movement must be greater than zero
	 * @param x the x position the unit is being told to move to, must be in the map
	 * @param y the y position the unit is being told to move to, must be in the map
	 */
	public void moveUnit(Unit u, double x, double y)
	{
		if(w.inWorld(x, y) && u.getMovement() > 0)
		{
			//System.out.println("unit ordered to move to ("+x+", "+y+")");
			double[] s = u.getLocation();
			Location[] l = w.getPathFinder().determinePath(s[0], s[1], x, y);
			//u.addAction(new Move(u, x, y));
			/*for(int i = 0; i < l.length; i++)
			{
				u.addAction(new Move(u, l[i].x, l[i].y));
			}*/
			u.addAction(new MoveList(u, l));
		}
	}
	/**
	 * the main AI method, called once by the SGEngine every iteration
	 * of the main thread
	 */
	public abstract void performAIFunctions();
	/**
	 * draws a UI for this ai
	 * @param gl
	 */
	public abstract void drawUI(GL gl);
	/**
	 * interprets the mouse click input from the user
	 * @param x the x position of the mouse click in game space
	 * @param y the y position of the mouse click in game space
	 * @param pressed true if the mouse is being pressed, false otherwise
	 * @param rightClick
	 */
	public abstract void interpretMouseClick(double x, double y, boolean pressed, boolean rightClick);
	/**
	 * interprets the mouse move input from the user
	 * @param x the x position of the mouse click in game space
	 * @param y the y position of the mouse click in game space
	 * @param dragged true if the mouse is being dragged, false otherwise
	 */
	public abstract void interpretMouseMove(double x, double y, boolean dragged, boolean rightClick);
}
