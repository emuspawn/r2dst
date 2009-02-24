package world;

import graphics.Camera;
import world.owner.*;
import java.awt.Graphics2D;
import utilities.Location;
import java.awt.Rectangle;
import world.action.Action;
import world.action.actions.Idle;

/**
 * the building blocks of the world, all things in the world are elements,
 * an element is defaulty of neutral ownership <br /><br />
 * 
 * a reference of each element is given to all classes that would modify
 * those elements, when one of the class declares the element to be dead
 * all classes remove their respective references to the element and thus
 * it is effectively removed from the game
 * 
 * @author Jack
 *
 */

public abstract class Element
{
	private String name;
	private Location l;
	private Action a = new Idle();
	protected Owner owner = new Neutral(); 
	
	protected boolean impassable = false; //determines if ground units can traverse the tile
	protected boolean liquid = false; //determines if naval units can traverse the tile
	
	private Rectangle bounds;
	private int width;
	private int height;
	
	private boolean dead = false; //if dead is true then it is removed for the game
	
	//resource variables
	protected boolean harvestable = false;
	protected int harvestMass = 0; //the amount of mass returned from harvesting this element
	
	public Element(String name, Location location, int width, int height)
	{
		this.name = name;
		this.width = width;
		this.height = height;
		setLocation(location);
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
	public int getHarvestMass()
	{
		return harvestMass;
	}
	public String toString()
	{
		return name+", "+getLocation()+", "+getBounds();
	}
	public void setOwner(Owner o)
	{
		owner = o;
	}
	public Owner getOwner()
	{
		return owner;
	}
	public Action getAction()
	{
		return a;
	}
	public boolean isImpassable()
	{
		return impassable;
	}
	public String getName()
	{
		return name;
	}
	public Rectangle getBounds()
	{
		return bounds;
	}
	public Location getLocation()
	{
		return l;
	}
	public void setLocation(Location location)
	{
		l = new Location(location.x, location.y);
		updateBounds();
	}
	private void updateBounds()
	{
		bounds = new Rectangle((int)l.x-width/2, (int)l.y-height/2, width, height);
	}
	public void setAction(Action a)
	{
		this.a = a;
	}
	public abstract void drawElement(Graphics2D g, Camera c);
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
}
