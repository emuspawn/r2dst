package mapEditor.listener;

import inputListener.MouseActionListener;
import mapEditor.*;
import java.awt.event.*;

public class MouseAction extends MouseActionListener
{
	MapEditor me;
	
	public MouseAction(MapEditor me)
	{
		this.me = me;
	}
	public void mouseClicked(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1 && me.getUICheckEngine() != null)
		{
			me.getUICheckEngine().testUIForClick(e.getPoint());
		}
	}
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			me.setLeftDown(true);
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			me.setRightDown(true);
		}
	}
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			me.setLeftDown(false);
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			me.setRightDown(false);
		}
	}
}
