package world.terrain;

import utilities.Location;
import java.awt.Graphics2D;
import java.awt.Color;
import graphics.Camera;
import java.awt.Point;

public class Water extends Terrain
{
	public Water(Location l, int width, int height)
	{
		super("water", l, width, height);
	}
	public void drawElement(Graphics2D g, Camera c)
	{
		g.setColor(Color.blue);
		Point p = c.getScreenLocation(getLocation());
		g.fillRect(p.x-getWidth()/2, p.y-getHeight()/2, getWidth(), getHeight());
	}
}
