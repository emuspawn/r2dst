package listener;

import driver.MainThread;
import java.awt.event.*;
import inputListener.MouseActionListener;

public class MouseAction extends MouseActionListener
{
	MainThread mt;
	
	public MouseAction(MainThread mt)
	{
		this.mt = mt;
	}
	public void mousePressed(MouseEvent e)
	{
		
	}
}
