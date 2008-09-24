package editor.mapEditor.listener;

import inputListener.MouseMotionActionListener;
import java.awt.event.*;
import editor.mapEditor.*;
import java.awt.Point;
import editor.mapEditor.tile.*;
import graphics.Camera;

public class MouseMotionAction extends MouseMotionActionListener
{
	MapEditor me;
	Camera c;
	
	public MouseMotionAction(MapEditor me)
	{
		this.me = me;
	}
	public void passCamera(Camera c)
	{
		this.c = c;
	}
	public void mouseMoved(MouseEvent e)
	{
		me.setMouseLocation(e.getPoint());
	}
	public void mouseDragged(MouseEvent e)
	{
		me.setMouseLocation(e.getPoint());
		if(c != null && me.getLeftDown())
		{
			createTile(new Point(e.getPoint().x+c.getxover(), e.getPoint().y+c.getyover()));
		}
		else if(c != null && me.getRightDown())
		{
			removeTile(new Point(e.getPoint().x+c.getxover(), e.getPoint().y+c.getyover()));
		}
	}
	private void removeTile(Point p)
	{
		Tile[] t = me.getTiles();
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] != null)
			{
				if(t[i].getBounds().contains(p))
				{
					t[i] = null;
					break;
				}
			}
		}
	}
	private void createTile(Point p)
	{
		//creates tiles at the upper left edge of the grid only and if not alread occupied
		int x = p.x - (p.x % me.getGridWidth());
		int y = p.y - (p.y % me.getGridHeight());
		Tile[] t = me.getTiles();
		Point location;
		boolean conflicts = false;
		if(x > me.getMapWidth() || y > me.getMapHeight())
		{
			conflicts = true;
		}
		if(!conflicts)
		{
			for(int i = 0; i < t.length; i++)
			{
				if(t[i] != null)
				{
					location = t[i].getLocation();
					if(location.x == x && location.y == y)
					{
						conflicts = true;
						break;
					}
				}
			}
			if(!conflicts)
			{
				me.addTile(new Tile(me.getEditType(), x, y));
			}
		}
	}
}
