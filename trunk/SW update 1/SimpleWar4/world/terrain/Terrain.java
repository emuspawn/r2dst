package world.terrain;

import java.awt.Polygon;
import java.awt.Graphics;

public abstract class Terrain
{
	int type;
	protected Polygon p;
	
	public Terrain(int type)
	{
		this.type = type;
	}
	public int getType()
	{
		return type;
	}
	public Polygon getBounds()
	{
		return p;
	}
	public abstract void drawTerrain(Graphics g);
}
