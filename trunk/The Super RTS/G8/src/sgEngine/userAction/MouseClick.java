package sgEngine.userAction;

import utilities.Location;
import world.owner.Owner;

public class MouseClick extends UserAction
{
	Location l;
	boolean rightClick;
	
	/**
	 * creates a mouse click to be interpreted in the game world
	 * @param l the location of the mouse click in the game world
	 * @param owner the user that initiated the mouse click
	 * @param rightClick true if the user right cliced, false otherwise
	 * @param runTime when the mouse click is to be put into effect
	 */
	public MouseClick(Location l, Owner owner, boolean rightClick, int runTime)
	{
		super("mouse click", owner, runTime);
		this.l = l;
		this.rightClick = rightClick;
	}
	/**
	 * gets the location of the mouse click
	 * @return returns the location of the mouse click
	 */
	public Location getLocation()
	{
		return l;
	}
	/**
	 * checks to see if the click was a right click or not
	 * @return returns true if right click, false otherwise
	 */
	public boolean isRightClick()
	{
		return rightClick;
	}
}
