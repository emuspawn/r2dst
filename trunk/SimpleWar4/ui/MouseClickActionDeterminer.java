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
	
	boolean menuHeaderClicked = false;
	
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
			if(!menuHeaderClicked)
			{
				actionPerformed = testForButtonClicked(e.getPoint());
				actionPerformed = testForMenuHeaderClicked(e.getPoint(), actionPerformed);
				actionPerformed = testForUnitClicked(e.getPoint(), actionPerformed);
			}
			else
			{
				actionPerformed = moveMenuHeader(e.getPoint());
			}
		}
		if(button == e.BUTTON3)
		{
			//right click
			actionPerformed = unhighlightAllUnits();
		}
		if(!actionPerformed)
		{
			moveHighlightedUnits(e.getPoint());
		}
	}
	private boolean moveMenuHeader(Point p)
	{
		Menu[] m = w.getMenuCheckEngine().getMenus();
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				if(m[i].getHeaderClicked())
				{
					m[i].setLocation(p);
					m[i].setHeaderClicked(false);
					menuHeaderClicked = false;
					break;
				}
			}
		}
		return true;
	}
	private boolean testForMenuHeaderClicked(Point p, boolean actionPerformed)
	{
		if(!actionPerformed)
		{
			Menu[] m = w.getMenuCheckEngine().getMenus();
			for(int i = 0; i < m.length; i++)
			{
				if(m[i] != null)
				{
					if(m[i].getVisible())
					{
						if(m[i].getMovable())
						{
							m[i].setHeaderClicked(true);
							menuHeaderClicked = true;
							return true;
						}
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
					System.out.println("unit unhighlighted");
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
					if(u[i].getVisibleBounds().contains(p))
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
