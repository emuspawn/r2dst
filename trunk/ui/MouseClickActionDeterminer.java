package ui;

import java.awt.event.*;
import world.unit.*;
import java.awt.Point;
import graphics.Camera;

public class MouseClickActionDeterminer
{
	UnitEngine ue;
	Camera c;
	
	public MouseClickActionDeterminer(UnitEngine ue, Camera c)
	{
		this.ue = ue;
		this.c = c;
	}
	public void performMouseActions(MouseEvent e, int button)
	{
		boolean leftClick = false;
		boolean actionPerformed = false;
		if(button == e.BUTTON1)
		{
			leftClick = true;
			actionPerformed = testForUnitClicked(e.getPoint());
		}
		if(!leftClick)
		{
			actionPerformed = unhighlightAllUnits();
		}
		if(!actionPerformed)
		{
			moveHighlightedUnits(e.getPoint());
		}
	}
	private void moveHighlightedUnits(Point p)
	{
		System.out.println("order sent to move highlighted units");
		ue.findUnitPaths(p);
	}
	private boolean unhighlightAllUnits()
	{
		Unit[] u = ue.getUnits();
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
	private boolean testForUnitClicked(Point p)
	{
		Unit[] u = ue.getUnits();
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
}
