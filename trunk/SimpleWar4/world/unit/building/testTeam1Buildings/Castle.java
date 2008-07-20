package world.unit.building.testTeam1Buildings;

import world.unit.*;
import graphics.Camera;
import world.controller.*;
import java.awt.Graphics;
import java.awt.Color;
import utilities.Location;

public class Castle extends Unit
{
	public Castle(Camera camera, Controller c, Location location)
	{
		super(camera, c, location);
		unitType = 2;
		length = 300;
		movement = 5;
	}
	public void drawUnit(Graphics g)
	{
		double zoomLevel = camera.getZoomLevel();
		int over = (int)(length * zoomLevel);
		//subtracts from x and y are to accomidate the units location as the center
		int x = (int)(camera.getVisibleLocation(location).x-(over/2.0));
		int y = (int)(camera.getVisibleLocation(location).y-(over/2.0));
		if(x != -1 && y != -1)
		{
			
			g.setColor(c.getPlayerColor());
			g.fillRect(x, y, over, over);
			
			g.setColor(Color.black);
			g.drawRect(x, y, over, over);
			//the turrets
			g.fillRect(x, y, (int)(over*.2), (int)(over*.2));
			g.fillRect(x+(int)(over*.8), y, (int)(over*.2), (int)(over*.2));
			g.fillRect(x, y+(int)(over*.8), (int)(over*.2), (int)(over*.2));
			g.fillRect(x+(int)(over*.8), y+(int)(over*.8), (int)(over*.2), (int)(over*.2));
			//the gate
			g.fillRect(x+(int)(over*.4), y+(int)(over*.8), (int)(over*.2), (int)(over*.2));
			//the walls
			g.fillRect(x+(int)(over*(1.0/15.0)), y+(int)(over*(3.0/15.0)), (int)(over*(1.0/15.0)), (int)(over*(9.0/15.0))); //top left connecting bottom left
			g.fillRect(x+(int)(over*(3.0/15.0)), y+(int)(over*(1.0/15.0)), (int)(over*(9.0/15.0)), (int)(over*(1.0/15.0))); //top left connecting top right
			g.fillRect(x+(int)(over*(13.0/15.0)), y+(int)(over*(3.0/15.0)), (int)(over*(1.0/15.0)), (int)(over*(9.0/15.0))); //top right connecting bottom left
			g.fillRect(x+(int)(over*(3.0/15.0)), y+(int)(over*(13.0/15.0)), (int)(over*(3.0/15.0)), (int)(over*(1.0/15.0))); //bottom left connecting gate
			g.fillRect(x+(int)(over*(9.0/15.0)), y+(int)(over*(13.0/15.0)), (int)(over*(3.0/15.0)), (int)(over*(1.0/15.0))); //bottom right connecting gate
			
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
