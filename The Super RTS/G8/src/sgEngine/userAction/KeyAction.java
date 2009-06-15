package sgEngine.userAction;

import java.awt.event.KeyEvent;

import graphics.GLCamera;
import utilities.Location;
import world.owner.Owner;

/**
 * the base class for handeling user key actions
 * @author Jack
 *
 */
public abstract class KeyAction extends UserAction
{
	private char character; //the character that was used
	private Location l; //where the camera was looking at when the key was pressed
	private KeyEvent e;
	
	public KeyAction(String name, KeyEvent e, Owner owner, GLCamera c, int runTime)
	{
		super(name, owner, runTime);
		this.character = e.getKeyChar();
		this.e = e;
		
		Location l = c.getLocation();
		
		Location vl = c.getViewLocation(); //view location
		Location vector = new Location(vl.x-l.x, vl.y-l.y, vl.z-l.z); //pos vector
		
		double height = 1; //the height off the ground of the click
		double lambda = (height-l.y)/vector.y;
		double x = l.x+lambda*vector.x;
		double z = l.z+lambda*vector.z;
		this.l = new Location(x, height, z);
	}
	public KeyEvent getKeyEvent()
	{
		return e;
	}
	/**
	 * gets the character that was pressed
	 * @return returns the character that was pressed
	 */
	public char getCharacter()
	{
		return character;
	}
	/**
	 * gets the location of where the camera was looking at on the map
	 * when the key was used
	 * @return
	 */
	public Location getLocation()
	{
		return l;
	}
}
