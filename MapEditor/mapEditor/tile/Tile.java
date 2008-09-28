package mapEditor.tile;

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;
import graphics.Camera;
import java.awt.Rectangle;

public class Tile
{
	protected Point p;
	protected int width = 30;
	protected int height = 30;
	int type;
	
	public static final int wall = 1;
	public static final int water = 2;
	public static final int floor = 3;
	public static final int dirt = 4;
	
	public Tile(int type, int x, int y)
	{
		this.type = type;
		p = new Point(x, y);
	}
	public Tile(int type, int x, int y, int width, int height)
	{
		//called by map loader
		this.type = type;
		p = new Point(x, y);
		this.width = width;
		this.height = height;
	}
	public Rectangle getBounds()
	{
		return new Rectangle(p.x, p.y, width, height);
	}
	public void drawTile(Graphics g, Camera c)
	{
		boolean onScreen = c.getOnScreen(getBounds());
		if(onScreen)
		{
			Point location = c.getScreenLocation(p);
			if(type == wall)
			{
				g.setColor(new Color(0, 0, 0));
				g.fillRect(location.x, location.y, width, height);
			}
			if(type == water)
			{
				g.setColor(new Color(0, 0, 255));
				g.fillRect(location.x, location.y, width, height);
			}
			if(type == floor)
			{
				g.setColor(new Color(192, 192, 192));
				g.fillRect(location.x, location.y, width, height);
			}
			if(type == dirt)
			{
				g.setColor(new Color(128, 64, 0));
				g.fillRect(location.x, location.y, width, height);
			}
		}
	}
	public Point getLocation()
	{
		return p;
	}
	public String toString()
	{
		return new String(type+" "+p.x+" "+p.y+" "+width+" "+height);
	}
}
