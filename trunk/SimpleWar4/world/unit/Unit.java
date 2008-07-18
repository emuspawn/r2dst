package world.unit;

import utilities.*;
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
	double movement = 5;
	protected Controller c;
	protected Rectangle bounds;
	Rectangle visibleBounds;
	protected boolean highlighted = false;
	protected Camera camera;
	protected int length; //how long one side of the square representing the unit is on a zoom level of 100%
	int movementType = 0; //specifies what type of ground the unit can move across
	
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
	private void setupVisibleUnitBounds()
	{
		int tempLength = (int)(length*camera.getZoomLevel());
		visibleBounds = new Rectangle((int)location.x-(tempLength/2)-camera.getxover(), (int)location.y-(tempLength/2)-camera.getyover(), tempLength, tempLength);
	}
	public Rectangle getVisibleBounds()
	{
		//the bounds as they appear to the user, for actions such as selecting the unit
		setupVisibleUnitBounds();
		return visibleBounds;
	}
	public Path getPath()
	{
		return path;
	}
	public abstract void drawUnit(Graphics g);
}
