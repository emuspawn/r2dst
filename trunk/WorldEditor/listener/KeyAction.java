package listener;

import inputListener.KeyActionListener;
import java.awt.event.*;
import driver.MainThread;

public class KeyAction extends KeyActionListener
{
	MainThread mt;
	
	public KeyAction(MainThread mt)
	{
		this.mt = mt;
	}
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			mt.upDown = true;
		}
		if(e.getKeyChar() == 'd')
		{
			mt.rightDown = true;
		}
		if(e.getKeyChar() == 's')
		{
			mt.downDown = true;
		}
		if(e.getKeyChar() == 'a')
		{
			mt.leftDown = true;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			mt.upDown = false;
		}
		if(e.getKeyChar() == 'd')
		{
			mt.rightDown = false;
		}
		if(e.getKeyChar() == 's')
		{
			mt.downDown = false;
		}
		if(e.getKeyChar() == 'a')
		{
			mt.leftDown = false;
		}
	}
}
