package world;

import world.action.Action;
import graphics.Camera;
import java.awt.Graphics2D;
import utilities.Location;
import world.action.actions.Idle;
import java.awt.Rectangle;

/*
 * the building blocks of the world, all things in the world are elements
 */

public abstract class Element
{
	private String name;
	protected Location l;
	Action a = new Idle();
	protected boolean impassable = false;
	
	protected Rectangle bounds;
	protected int width;
	protected int height;
	
	public Element(String name, Location location, int width, int height)
	{
		this.name = name;
		this.width = width;
		this.height = height;
		setLocation(location);
	}
	public boolean isImpassable()
	{
		return impassable;
	}
	public void translate(double x, double y)
	{
		l.x = l.x+x;
		l.y = l.y+y;
		updateBounds();
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
	public abstract void drawElementLG(Graphics2D g, Camera c); //draws the low graphics representation of the element
}
