package world.unit.infantry;

import world.unit.*;
import java.awt.Graphics;
import utilities.Location;
import world.controller.*;
import java.awt.Color;
import graphics.Camera;
import java.awt.Rectangle;

public class BasicInfantry extends Unit
{
	public BasicInfantry(Camera camera, Controller c, Location location)
	{
		super(camera, c, location);
		length = 30;
		setMovement(20);
		setName("Inf");
	}
	public void drawUnit(Graphics g)
	{
		int x = (int)camera.getVisibleLocation(location).x;
		int y = (int)camera.getVisibleLocation(location).y;
		if(x != -1 && y != -1)
		{
			double zoomLevel = camera.getZoomLevel();
			int over = (int)(length * camera.getZoomLevel());
			g.setColor(Color.black);
			g.drawRect(x-(over/2), y-(over/2), over, over);
			g.setColor(c.getPlayerColor());
			g.fillRect(x-(over/2), y-(over/2), over, over);
			g.setColor(Color.black);
			g.drawString(name, x-(over/4), y);
			
			//draws the unit bounds
			Rectangle r = getVisibleBounds();
			if(r != null)
			{
				g.setColor(Color.cyan);
				g.drawRect(r.x, r.y, r.width, r.height);
			}
			
			if(highlighted)
			{
				over = (int)(length+10 * zoomLevel);
				g.setColor(Color.orange);
				g.drawOval(x-(over/2), y-(over/2), over, over);
				g.drawOval(x-(over/2)+1, y-(over/2)+1, over, over);
				g.drawOval(x-(over/2)-1, y-(over/2)-1, over, over);
			}
		}
	}
}
