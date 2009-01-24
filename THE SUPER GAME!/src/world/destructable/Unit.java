package world.destructable;

import java.awt.Graphics2D;
import java.io.Serializable;

import graphics.Camera;
import utilities.Location;
import world.World;

public class Unit extends Destructable implements Serializable
{
	private static final long serialVersionUID = 1L;
	public Unit(String name, Location l, int width, int height)
	{
		super(name, l, width, height);
	}
	public void drawElementLG(Graphics2D g, Camera c)
	{
		
	}
	public void destroy(World w)
	{
		
	}
	public int getElementType() {
		return 1;
	}
}
