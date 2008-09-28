package rpgWorld.unit;

import utilities.Location;
import graphics.Camera;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics;

public abstract class Unit
{
	protected Location location; //at the center of the unit
	protected int size = 30;
	private int movement = 5;
	protected double angle = 0; //in degrees, moves rotationSpeed per thread iteration
	private double rotationSpeed = 7; //degrees
	
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int LEFT = 4;
	public Unit(Point p)
	{
		location = new Location(p);
	}
	public Rectangle getBounds()
	{
		return new Rectangle((int)location.x, (int)location.y, size, size);
	}
	public Location getLocation()
	{
		return location;
	}
	public void rotatePositive()
	{
		angle+=rotationSpeed;
		angle = angle % 360;
	}
	public void rotateNegative()
	{
		angle-=rotationSpeed;
		angle = angle % 360;
	}
	public void moveUnit(int direction)
	{
		if(direction == UP)
		{
			location.y-=movement;
		}
		else if(direction == RIGHT)
		{
			location.x+=movement;
		}
		else if(direction == DOWN)
		{
			location.y+=movement;
		}
		else if(direction == LEFT)
		{
			location.x-=movement;
		}
	}
	public void moveUnit()
	{
		double rangle = Math.toRadians(angle);
		location = new Location(location.x+(Math.cos(rangle)*movement), location.y+(Math.sin(rangle)*movement));
	}
	public abstract void drawUnit(Graphics g, Camera c);
}