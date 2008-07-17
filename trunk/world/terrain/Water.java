package world.terrain;

import java.awt.Graphics;
import java.awt.Color;

public class Water extends Terrain
{
	public Water()
	{
		super(2);
	}
	public void drawTerrain(Graphics g)
	{
		g.setColor(Color.blue);
		g.fillPolygon(p);
	}
}
