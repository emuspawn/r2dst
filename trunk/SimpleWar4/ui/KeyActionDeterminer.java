package ui;

import java.awt.event.*;
import world.World;
import utilities.Location;
import graphics.Camera;
import world.unit.*;
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
		if(e.getKeyChar() == e.VK_ESCAPE && !w.getEditMode())
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
			w.getUnitEngine().registerUnit(new BasicInfantry(c, new Controller(1, Color.red), new Location(90, 110)));
		}
		else if(e.getKeyChar() == 'w')
		{
			c.setZoomLevel(c.getZoomLevel()+.05);
			System.out.println("zoom level = "+c.getZoomLevel());
		}
		else if(e.getKeyChar() == 's')
		{
			if(c.getZoomLevel() > .28)
			{
				c.setZoomLevel(c.getZoomLevel()-.05);
				System.out.println("zoom level = "+c.getZoomLevel());
			}
		}
		else if(e.getKeyChar() == 'q' && w.getEditMode())
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
