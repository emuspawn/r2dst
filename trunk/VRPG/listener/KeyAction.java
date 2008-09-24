package listener;

import inputListener.KeyActionListener;
import driver.MainThread;
import java.awt.event.*;

public class KeyAction extends KeyActionListener
{
	MainThread  mt;
	
	public KeyAction(MainThread mt)
	{
		this.mt = mt;
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			mt.setUp(true);
		}
		if(e.getKeyChar() == 'a')
		{
			mt.setLeft(true);
		}
		if(e.getKeyChar() == 's')
		{
			mt.setDown(true);
		}
		if(e.getKeyChar() == 'd')
		{
			mt.setRight(true);
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			mt.setUp(false);
		}
		if(e.getKeyChar() == 'a')
		{
			mt.setLeft(false);
		}
		if(e.getKeyChar() == 's')
		{
			mt.setDown(false);
		}
		if(e.getKeyChar() == 'd')
		{
			mt.setRight(false);
		}
	}
}
