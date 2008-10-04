package drawing;

import java.awt.*;
import driver.MainThread;
import tileSystem.TileSystem;
import graphics.Camera;

public class Drawer
{
	Camera c;
	DrawData dd;
	MainThread mt;
	TileSystem ts;
	
	public Drawer(MainThread mt, Camera c, DrawData dd, TileSystem ts)
	{
		this.c = c;
		this.dd = dd;
		this.mt = mt;
		this.ts = ts;
	}
	public void performDrawFunctions(Graphics g)
	{
		g.setColor(Color.green);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());

		ts.drawTiles(g, c);
		drawGrid(g);
	}
	private void drawGrid(Graphics g)
	{
		if(dd.getDrawGrid())
		{
			g.setColor(Color.black);
			Point p1;
			Point p2;
			for(int i = 0; i < mt.getMapWidth(); i+=dd.getGridSize()) //i+=gridWidth
			{
				p1 = c.getScreenLocation(new Point(i, 0));
				p2 = c.getScreenLocation(new Point(i, mt.getMapHeight()));
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
			for(int i = 0; i < mt.getMapHeight(); i+=dd.getGridSize()) //i+=gridHeight
			{
				p1 = c.getScreenLocation(new Point(0, i));
				p2 = c.getScreenLocation(new Point(mt.getMapWidth(), i));
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
			p1 = c.getScreenLocation(new Point(0, 0));
			g.drawRect(p1.x, p1.y, mt.getMapWidth(), mt.getMapHeight());
		}
	}
}
