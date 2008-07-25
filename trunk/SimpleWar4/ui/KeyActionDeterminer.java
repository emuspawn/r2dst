package ui;

import java.awt.event.*;
import world.unit.building.*;
import world.unit.building.testTeam1Buildings.*;
import world.World;
import utilities.Location;
import graphics.Camera;
import world.unit.infantry.*;
import world.controller.*;
import java.awt.Color;

public class KeyActionDeterminer
{
	World w;
	Camera c;
	
	public KeyActionDeterminer(World w, Camera c)
	{
		this.w = w;
		this.c = c;
	}
	public void performKeyActions(KeyEvent e)
	{
		if(e.getKeyChar() == e.VK_ESCAPE && w.getRunSpecification().getMode() == 2)
		{
			if(w.getMenuCheckEngine().getMenu(1).getVisible())
			{
				w.getMenuCheckEngine().getMenu(1).setVisible(false);
			}
			else
			{
				w.getMenuCheckEngine().getMenu(1).setVisible(true);
			}
		}
		else if(e.getKeyChar() == 'u')
		{
			w.getUnitEngine().registerUnit(new BasicInfantry(c, new Controller(1, Color.red, null), new Location(90, 110)));
		}
		else if(e.getKeyChar() == 'd')
		{
			w.getUnitEngine().registerUnit(new DivineAnchor(c, new Controller(1, Color.red, null), new Location(300, 300)));
			w.getUnitEngine().registerUnit(new Castle(c, new Controller(1, Color.red, null), new Location(700, 500)));
		}
		else if(e.getKeyChar() == 'w')
		{
			if(c.getZoomLevel() < 1.51)
			{
				c.setZoomLevel(c.getZoomLevel()+.05);
				System.out.println("zoom level = "+c.getZoomLevel());
			}
		}
		else if(e.getKeyChar() == 's')
		{
			if(c.getZoomLevel() > .28)
			{
				c.setZoomLevel(c.getZoomLevel()-.05);
				System.out.println("zoom level = "+c.getZoomLevel());
			}
		}
		else if(e.getKeyChar() == 'q' && w.getRunSpecification().getMode() == 1)
		{
			if(w.getMenuCheckEngine().getMenu(1).getVisible())
			{
				w.getMenuCheckEngine().getMenu(1).setVisible(false);
			}
			else
			{
				w.getMenuCheckEngine().getMenu(1).setVisible(true);
			}
		}
	}
}
