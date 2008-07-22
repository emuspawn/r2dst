package world.unit;

import utilities.*;

import java.awt.Color;
import java.awt.Graphics;
import world.controller.*;
import java.awt.Rectangle;
import world.pathFinder.*;
import graphics.Camera;

public abstract class Unit
{
	int life = 10;
	int magic = 0;
	protected Location location;
	protected String name;
	protected double movement = 5;
	protected Controller c;
	Rectangle bounds;
	protected boolean highlighted = false;
	protected Camera camera;
	protected int length; //how long one side of the square representing the unit is on a zoom level of 100%
	int movementType = 0; //specifies what type of ground the unit can move across
	protected int unitType = 1;
	
	/*
	 * unit type:
	 * 1=unit
	 * 2=building
	 */
	
	//building variables
	
	
	//path finding variables
	Path path;
	Location pointMovingTo;
	boolean moving = false;
	Location destination;
	
	/*
	 * movement type:
	 * 1=land
	 * 2=water
	 */
	
	public Unit(Camera camera, Controller c, Location location)
	{
		this.location = location;
		this.c = c;
		this.camera = camera;
		//setupUnitBounds();
	}
	public int getUnitType()
	{
		return unitType;
	}
	public Location getDestination()
	{
		return destination;
	}
	public void setMovementType(int setter)
	{
		movementType = setter;
	}
	public int getMovementType()
	{
		return movementType;
	}
	public void setPointMovingTo(Location l)
	{
		pointMovingTo = l;
	}
	public Location getPointMovingTo()
	{
		return pointMovingTo;
	}
	public void setDestination(Location l)
	{
		destination = l;
	}
	public void setHighlighted(boolean setter)
	{
		highlighted = setter;
	}
	public boolean getHighlighted()
	{
		return highlighted;
	}
	public boolean getMoving()
	{
		return moving;
	}
	public void setMoving(boolean setter)
	{
		moving = setter;
	}
	public double getMovement()
	{
		return movement;
	}
	public void setMovement(double setter)
	{
		movement = setter;
	}
	public Location getLocation()
	{
		return location;
	}
	public void setLocation(Location l)
	{
		location = l;
		//setupUnitBounds();
	}
	public void setPath(Path p)
	{
		path = p;
	}
	public void setLife(int setter)
	{
		life = setter;
	}
	public int getLife()
	{
		return life;
	}
	public void setMagic(int setter)
	{
		magic = setter;
	}
	public int getMagic()
	{
		return magic;
	}
	public void setName(String setter)
	{
		name = setter;
	}
	public String getName()
	{
		return name;
	}
	public Rectangle getBounds()
	{
		//returns the bounds of the unit in game space, may not be what is actually displayed
		//for things like collision detecting
		setupUnitBounds();
		return bounds;
	}
	private void setupUnitBounds()
	{
		bounds = new Rectangle((int)location.x-(length/2), (int)location.y-(length/2), length, length);
	}
	public void drawUnitVisibleBounds(Graphics g)
	{
		Rectangle r = getVisibleBounds();
		int tempLength = (int)(length*camera.getZoomLevel());
		if(r != null)
		{
			if((int)(r.x+(tempLength/2)) != -1 && (int)(r.y+(tempLength/2)) != -1)
			{
				g.setColor(Color.cyan);
				g.drawRect(r.x, r.y, r.width, r.height);
			}
		}
	}
	public Rectangle getVisibleBounds()
	{
		//the bounds as they appear to the user, for actions such as selecting the unit
		int tempLength = (int)(length*camera.getZoomLevel());
		int x = (int)(camera.getVisibleLocation(location).x);
		int y = (int)(camera.getVisibleLocation(location).y);
		return new Rectangle(x-(tempLength/2), y-(tempLength/2), tempLength, tempLength);
	}
	public Path getPath()
	{
		return path;
	}
	public abstract void drawUnit(Graphics g);
}
