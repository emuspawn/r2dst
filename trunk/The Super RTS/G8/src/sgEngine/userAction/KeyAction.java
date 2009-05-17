package sgEngine.userAction;

import java.awt.geom.AffineTransform;

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
	
	public KeyAction(String name, char character, Owner owner, GLCamera c, int runTime)
	{
		super(name, owner, runTime);
		this.character = character;
		
		double ydiff = c.getLocation().y-c.getViewLocation().y;
		double zdiff = c.getLocation().z-c.getViewLocation().z;
		double yzslope = ydiff / zdiff;
		double height = 1; //height of the selector
		double depth = ((height-c.getLocation().y)/yzslope)+c.getLocation().z;
		
		l = new Location(c.getLocation().x, height, depth);
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(c.getRotation()));
		double[] lPoints = {l.x, l.z};
		double[] newlPoints = new double[2];
		at.transform(lPoints, 0, newlPoints, 0, 1);
		l = new Location(newlPoints[0], l.y, newlPoints[1]);
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
