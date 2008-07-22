package world.unit.building;

import world.unit.*;
import graphics.Camera;
import world.controller.*;
import java.awt.Graphics;
import java.awt.Color;
import utilities.Location;

public class DivineAnchor extends Unit
{
	public DivineAnchor(Camera camera, Controller c, Location location)
	{
		super(camera, c, location);
		unitType = 2;
		length = 60;
		movement = 25;
	}
	public void drawUnit(Graphics g)
	{
		int x = (int)(camera.getVisibleLocation(this).x);
		int y = (int)(camera.getVisibleLocation(this).y);
		if(x != -1 && y != -1)
		{
			double zoomLevel = camera.getZoomLevel();
			int over = (int)(length * zoomLevel);
			x = (int)(x-(over/2.0));
			y = (int)(y-(over/2.0));
			
			g.setColor(c.getPlayerColor());
			g.fillRect(x, y, over, over);
			g.setColor(Color.black);
			g.drawRect(x, y, over, over);
			g.drawLine(x, y, x+over, y+over);
			g.drawLine(x, y+over, x+over, y);
			g.fillOval(x+(over/2)-(int)((20*zoomLevel)/2), y+(over/2)-(int)((20*zoomLevel)/2), (int)(20*zoomLevel), (int)(20*zoomLevel));
			
			if(highlighted)
			{
				g.setColor(Color.orange);
				g.drawOval(x, y, over, over);
				g.drawOval(x+1, y+1, over, over);
				g.drawOval(x-1, y-1, over, over);
			}
		}
	}
}