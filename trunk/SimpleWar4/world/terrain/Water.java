package world.terrain;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;

public class Water extends Terrain
{
	public Water(Polygon polygon)
	{
		super(2, polygon);
	}
	public void drawTerrain(Graphics g, int xover, int yover)
	{
		Polygon poly = p;
		poly.translate(-1*xover, -1*yover);
		g.setColor(Color.blue);
		g.fillPolygon(poly);
	}
}
