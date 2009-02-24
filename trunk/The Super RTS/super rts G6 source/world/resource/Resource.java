package world.resource;

import graphics.Camera;
import utilities.Location;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public abstract class Resource
{
	String name;
	Location location;
	Rectangle bounds;
	int size;
	Color c;
	boolean visible = true; //if loaded, not visible
	
	public Resource(Location location, String name, Color c, int size)
	{
		setLocation(location);
		this.name = name;
		this.c = c;
		this.size = size;
	}
	public String getName()
	{
		return name;
	}
	public void setVisible(boolean setter)
	{
		visible = setter;
	}
	public Location getLocation()
	{
		return location;
	}
	public void setLocation(Location l)
	{
		location = l;
		setupBounds();
	}
	private void setupBounds()
	{
		bounds = new Rectangle((int)location.x/2, (int)location.y/2, size, size);
	}
	public void drawResource(Graphics g, Camera c)
	{
		if(c.getOnScreen(bounds) && visible)
		{
			g.setColor(this.c);
			int x = c.getScreenLocation(location).x;
			int y = c.getScreenLocation(location).y;
			g.fillOval(x-size/2, y-size/2, size, size);
			String sub = name.substring(0, 3);
			g.setColor(Color.black);
			g.drawString(sub, x-9, y);
		}
	}
}
