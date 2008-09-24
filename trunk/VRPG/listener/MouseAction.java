package listener;

import inputListener.MouseActionListener;
import driver.MainThread;
import java.awt.event.*;

public class MouseAction extends MouseActionListener
{
	MainThread mt;
	
	public MouseAction(MainThread mt)
	{
		this.mt = mt;
	}
	public void mouseClicked(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}
