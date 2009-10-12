package dynamicMap;

import java.awt.Rectangle;
import utilities.Location;

/**
 * a basic part, designed so that references of this
 * are spread across the program then, when one part
 * of the program declares this to be dead, all other
 * parts remove it from the program
 * @author Jack
 *
 */
public abstract class Region
{
	String name;
	Location l;
	int width;
	int height;
	boolean dead = false;
	
	public Region(String name, Location l, int width, int height)
	{
		this.name = name;
		this.l = l;
		this.width = width;
		this.height = height;
	}
	public Location getLocation()
	{
		return l;
	}
	/**
	 * sets the location of the region to the passed
	 * location
	 * @param l the new location of the region
	 */
	public void setLocation(Location l)
	{
		this.l = l;
	}
	public void translate(double xover, double yover)
	{
		l = new Location(l.x+xover, l.y+yover);
	}
	/**
	 * gets the name of this region
	 * @return returns the region's name
	 */
	public String getName()
	{
		return name;
	}
	public Rectangle getBounds()
	{
		return new Rectangle((int)l.x-width/2, (int)l.y-height/2, width, height);
	}
	/**
	 * checks to see if Element is dead and needs to be removed
	 * @return returns whether or not the element is dead
	 */
	public boolean isDead()
	{
		return dead;
	}
	/**
	 * sets the element to dead
	 */
	public void setDead()
	{
		dead = true;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public String toString()
	{
		return getName()+", "+getLocation()+", w = "+getWidth()+", h = "+getHeight();
	}
}
