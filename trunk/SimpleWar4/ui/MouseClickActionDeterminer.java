package ui;

import java.awt.event.*;
import world.unit.*;
import java.awt.Point;
import graphics.Camera;
import ui.menu.*;
import ui.button.*;
import world.World;

public class MouseClickActionDeterminer
{
	World w;
	Camera c;
	
	public MouseClickActionDeterminer(World w, Camera c)
	{
		this.w = w;
		this.c = c;
	}
	public void performMouseActions(MouseEvent e, int button)
	{
		boolean actionPerformed = false;
		if(button == e.BUTTON1)
		{
			//left click
			actionPerformed = testForButtonClicked(e.getPoint());
			actionPerformed = testForUnitClicked(e.getPoint(), actionPerformed);
		}
		if(button == e.BUTTON1)
		{
			//right click
			actionPerformed = unhighlightAllUnits();
		}
		if(!actionPerformed)
		{
			moveHighlightedUnits(e.getPoint());
		}
	}
	private boolean testForButtonClicked(Point p)
	{
		Menu[] m = w.getMenuCheckEngine().getMenus();
		Button[] b;
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				if(m[i].getVisible())
				{
					b = m[i].getButtons();
					for(int a = 0; a < b.length; a++)
					{
						if(b[a] != null)
						{
							if(b[a].getBounds().contains(p))
							{
								b[a].setClicked(true);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	private void moveHighlightedUnits(Point p)
	{
		System.out.println("order sent to move highlighted units");
		w.getUnitEngine().findUnitPaths(p);
	}
	private boolean unhighlightAllUnits()
	{
		Unit[] u = w.getUnitEngine().getUnits();
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				if(u[i].getHighlighted())
				{
					u[i].setHighlighted(false);
				}
			}
		}
		return true;
	}
	private boolean testForUnitClicked(Point p, boolean actionPerformed)
	{
		if(!actionPerformed)
		{
			Unit[] u = w.getUnitEngine().getUnits();
			for(int i = 0; i < u.length; i++)
			{
				if(u[i] != null)
				{
					if(u[i].getBounds().contains(p))
					{
						System.out.println("unit highlighted");
						u[i].setHighlighted(true);
						return true;
					}
				}
			}
			return false;
		}
		else
		{
			return false;
		}
	}
}
