package ui;

import java.awt.event.*;
import utilities.Location;
import graphics.Camera;

import world.unit.*;
import world.unit.infantry.*;
import world.controller.*;
import java.awt.Color;

public class KeyActionDeterminer
{
	UnitEngine ue;
	Camera c;
	
	public KeyActionDeterminer(UnitEngine ue, Camera c)
	{
		this.ue = ue;
		this.c = c;
	}
	public void performKeyActions(KeyEvent e)
	{
		if(e.getKeyChar() == e.VK_ESCAPE)
		{
			System.exit(0);
		}
		else if(e.getKeyChar() == 'u')
		{
			ue.registerUnit(new BasicInfantry(c, new Controller(1, Color.red), new Location(90, 110)));
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
	}
}
