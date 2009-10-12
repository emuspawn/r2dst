package graphics;

import java.awt.*;
import utilities.Location;

public final class Camera
{
	private int xover = 0;
	private int yover = 0;
	private int width; //the displayed width
	private int height; //the displayed height
	private double scale = 1;
	private double rotation = 0; //angle the camera is rotated
	
	public Camera(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	public double getRotatation()
	{
		return rotation;
	}
	public void setRotation(double setter)
	{
		rotation = setter;
	}
	public double getScale()
	{
		return scale;
	}
	public void setScale(double setter)
	{
		scale = setter;
	}
	/**
	 * how far over the camera has been moved to in the x plane
	 * @return returns the x coordinate of top left point of the viewable area
	 */
	public int getxover()
	{
		return xover;
	}
	/**
	 * how far over the camera has been moved to in the y plane
	 * @return returns the y coordinate of top left point of the viewable area
	 */
	public int getyover()
	{
		return yover;
	}
	public void setWidth(int setter)
	{
		width = setter;
	}
	public void setHeight(int setter)
	{
		height = setter;
	}
	/**
	 * gets the width of the viewable area of the screen in game space
	 * @return the returns screen width
	 */
	public int getWidth()
	{
		return width;
	}
	/**
	 * gets the height of the viewable area of the screen in game space
	 * @return the returns screen height
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * tests to see if the passed rectangle is on sreen
	 * @param r the rectangle being tested
	 * @return returns true if the rectangle is on screen, false otherwise
	 */
	public boolean isOnScreen(Rectangle r)
	{
		Rectangle sbounds = new Rectangle(xover, yover, width, height);
		if(sbounds.intersects(r) || sbounds.contains(r))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public String toString()
	{
		return "xover = "+xover+", yover = "+yover+", width = "+width+", height = "+height;
	}
	public void translate(int x, int y)
	{
		xover += x;
		yover += y;
	}
	public Point getScreenLocation(Point p)
	{
		//returns the location of an item on the screen given its game space coordinates
		return new Point(p.x-xover, p.y-yover);
	}
	public Point getScreenLocation(Location l)
	{
		return getScreenLocation(new Point((int)l.x, (int)l.y));
	}
	public void centerOn(Point p)
	{
		xover = p.x - (width/2);
		yover = p.y - (height/2);
	}
	public void centerOn(Location l)
	{
		centerOn(new Point((int)l.x, (int)l.y));
	}
}
