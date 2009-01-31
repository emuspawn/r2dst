package world;

import world.action.Action;
import graphics.Camera;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import utilities.Location;
import world.action.actions.Idle;
import java.awt.Rectangle;
import java.io.Serializable;

/*
 * the building blocks of the world, all things in the world are elements
 */

public class Element
{
	private String name;
	protected Location l;
	Action a = new Idle();
	protected boolean impassable = false;
	protected int shapeType;
	protected Color clr;
	
	public Rectangle bounds;
	public int width;
	public int height;
	
	public Element(String name, Location location, int width, int height)
	{
		this.name = name;
		this.width = width;
		this.height = height;
		setLocation(location);
	}
	public Element(int shapeType, Color clr, boolean impassable, Location loc, int width, int height)
	{
		this("", loc, width, height);
		this.clr = clr;
		this.shapeType = shapeType;
		this.impassable = impassable;
	}
	public int getShapeType()
	{
		return shapeType;
	}
	public Color getColor()
	{
		return clr;
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
	public void drawElementLG(Graphics2D g, Camera cam)
	{
		Point pt = cam.getScreenLocation(l);
		
		g.setColor(clr);
		
		if (shapeType == 0)
			g.fillOval(pt.x-width/2, pt.y-height/2, width, height);
		else if (shapeType == 1)
			g.fillRect(pt.x-width/2, pt.y-height/2, width, height);
		else {
			System.out.println("Bad shapeType!");
			while (true);
		}	
	}
}
