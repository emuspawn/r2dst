package sgEngine.userAction;

import java.awt.event.MouseEvent;

import utilities.Location;
import world.owner.Owner;

public class MouseAction extends UserAction
{
	/**
	 * represents a mouse press
	 */
	public static final byte press = 1;
	/**
	 * represents a mouse release
	 */
	public static final byte release = 2;
	/**
	 * represents a mouse click
	 */
	public static final byte click = 3;
	
	Location l;
	boolean rightClick;
	byte type;
	Location screenLocation; //where the user clicked on the screen
	MouseEvent e;
	
	/**
	 * creates a mouse click to be interpreted in the game world
	 * @param l the location of the mouse click in the game world
	 * @param screenLocation where the user clicked on screen
	 * @param owner the user that initiated the mouse click
	 * @param rightClick true if the user right cliced, false otherwise
	 * @param type the type of click, press, release, or click (all constants)
	 * @param runTime when the mouse click is to be put into effect
	 */
	public MouseAction(Location l, Location screenLocation, MouseEvent e, Owner owner, boolean rightClick, byte type, int runTime)
	{
		super("mouse click", owner, runTime);
		this.l = l;
		this.screenLocation = screenLocation;
		this.rightClick = rightClick;
		this.type = type;
		this.e = e;
	}
	public MouseEvent getMouseEvent()
	{
		return e;
	}
	/**
	 * gets the location of the mouse click in the game world
	 * @return returns the location of the mouse click in the game world
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
	/**
	 * returns the type of mouse action this was, click, press, or release
	 * @return returns the type of mouse action
	 */
	public byte getType()
	{
		return type;
	}
	/**
	 * gets the location of where the user clicked on the screen
	 * @return returns the location of where the user clicked on screen
	 */
	public Location getScreenLocation()
	{
		return screenLocation;
	}
}
