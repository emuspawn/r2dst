package graphics;

import java.awt.*;

public class Camera
{
	private int xover = 0;
	private int yover = 0;
	private int width; //the displayed width
	private int height; //the displayed height
	boolean drawRegardless = false; //for saving purposes, everything drawn
	
	public Camera(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	public Camera()
	{
		drawRegardless = true;
	}
	public int getxover()
	{
		return xover;
	}
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
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public boolean getOnScreen(Rectangle r)
	{
		if(!drawRegardless)
		{
//			returns whether or not the rectangle is on the screen
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
		else
		{
			return true;
		}
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
	public void centerOn(Point p)
	{
		//System.out.println(p);
		xover = p.x - (width/2);
		//xover = p.x;
		//System.out.println(p.x+" - "+(width/2)+" = "+xover);
		yover = p.y - (height/2);
		//System.out.println(p.y+" - "+(height/2)+" = "+yover);
	}
}
