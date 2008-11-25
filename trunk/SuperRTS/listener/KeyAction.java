package listener;

import java.awt.event.*;
import graphics.Camera;

public class KeyAction implements KeyListener
{
	Camera c;
	
	public KeyAction(Camera c)
	{
		this.c = c;
	}
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			c.translate(0, -10);
		}
		if(e.getKeyChar() == 'd')
		{
			c.translate(10, 0);
		}
		if(e.getKeyChar() == 's')
		{
			c.translate(0, 10);
		}
		if(e.getKeyChar() == 'a')
		{
			c.translate(-10, 0);
		}
	}
}
