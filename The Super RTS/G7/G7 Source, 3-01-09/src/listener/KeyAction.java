package listener;

import java.awt.event.*;
import graphics.Camera;

public class KeyAction implements KeyListener
{
	boolean moveUp = false;
	boolean moveRight = false;
	boolean moveDown = false;
	boolean moveLeft = false;
	
	public void updateCamera(Camera c)
	{
		int scrollAmount = 9;
		if(moveUp)
		{
			c.translate(0, -scrollAmount);
		}
		if(moveRight)
		{
			c.translate(scrollAmount, 0);
		}
		if(moveDown)
		{
			c.translate(0, scrollAmount);
		}
		if(moveLeft)
		{
			c.translate(-scrollAmount, 0);
		}
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			moveUp = true;
		}
		if(e.getKeyChar() == 'd')
		{
			moveRight = true;
		}
		if(e.getKeyChar() == 's')
		{
			moveDown = true;
		}
		if(e.getKeyChar() == 'a')
		{
			moveLeft = true;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			moveUp = false;
		}
		if(e.getKeyChar() == 'd')
		{
			moveRight = false;
		}
		if(e.getKeyChar() == 's')
		{
			moveDown = false;
		}
		if(e.getKeyChar() == 'a')
		{
			moveLeft = false;
		}
	}
	public void keyTyped(KeyEvent e){}
}
