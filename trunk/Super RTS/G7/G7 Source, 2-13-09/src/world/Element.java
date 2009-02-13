package world;

import graphics.Camera;
import world.owner.*;
import java.awt.Graphics2D;
import utilities.Location;
import java.awt.Rectangle;
import world.action.Action;
import world.action.actions.Idle;

/*
 * the building blocks of the world, all things in the world are elements,
 * an element is defaulty of neutral ownership
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
	
	//resource variables
	protected boolean harvestable = false;
	protected int mass = 0;
	
	public Element(String name, Location location, int width, int height)
	{
		this.name = name;
		this.width = width;
		this.height = height;
		setLocation(location);
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
