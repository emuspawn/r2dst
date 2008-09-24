package editor.mapEditor.listener;

import inputListener.KeyActionListener;
import java.awt.Point;
import ui.menu.*;
import java.awt.event.*;
import editor.mapEditor.*;
import graphics.GraphicsFinder;

public class KeyAction extends KeyActionListener
{
	MapEditor me;
	
	public KeyAction(MapEditor me)
	{
		this.me = me;
	}
	public void keyTyped(KeyEvent e)
	{
		if(e.getKeyChar() == KeyEvent.VK_ESCAPE)
		{
			//opens main menu
			if(me.getUICheckEngine().getMenuCheckEngine().getMenuGroup("main").getMenu(1).getVisible())
			{
				me.getUICheckEngine().getMenuCheckEngine().getMenuGroup("main").getMenu(1).setVisible(false);
			}
			else
			{
				me.getUICheckEngine().getMenuCheckEngine().getMenuGroup("main").getMenu(1).setVisible(true);
			}
		}
		else if(me.getUICheckEngine().getInputBoxCheckEngine().getVisibleInputBox() == null)
		{
			//opens terrain menu
			if(e.getKeyChar() == 'q')
			{
				if(me.getUICheckEngine().getMenuCheckEngine().getMenuGroup("chooser").getMenu(1).getVisible())
				{
					me.getUICheckEngine().getMenuCheckEngine().getMenuGroup("chooser").getMenu(1).setVisible(false);
				}
				else
				{
					Menu m = me.getUICheckEngine().getMenuCheckEngine().getMenuGroup("chooser").getMenu(1);
					GraphicsFinder gf = me.getGraphicsFinder();
					m.setLocation(new Point(gf.getWidth()-m.getSlotWidth(), 0));
					m.setVisible(true);
				}
			}
		}
		else
		{
			me.getUICheckEngine().testUIForKeyInput(e.getKeyChar());
		}
	}
}
