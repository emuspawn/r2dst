package listener;

import inputListener.MouseMotionActionListener;
import java.awt.event.*;
import driver.MainThread;

public class MouseMotionAction extends MouseMotionActionListener
{
	MainThread mt;
	
	public MouseMotionAction(MainThread mt)
	{
		this.mt = mt;
	}
	public void mouseMoved(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
}
