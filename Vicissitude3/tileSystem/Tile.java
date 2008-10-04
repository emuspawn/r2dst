package tileSystem;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Graphics;
import graphics.Camera;

public class Tile
{
	Point p;
	int width = 30;
	int height = 30;
	int type;
	
	public Tile(int type, int x, int y)
	{
		this.type = type;
		p = new Point(x, y);
	}
	public Rectangle getBounds()
	{
		return new Rectangle(p.x, p.y, 30, 30);
	}
	public void drawTile(Graphics g, Camera c, TileTypeRegistry ttr)
	{
		g.setColor(ttr.getTileType(type).getColor());
		Point temp = c.getScreenLocation(p);
		g.fillRect(temp.x, temp.y, width, height);
	}
	public Point getLocation()
	{
		return p;
	}
}
