package listener;

import java.awt.event.*;
import display.worldDisplay.DrawCanvas;

public class KeyAction implements KeyListener
{
	DrawCanvas dc;
	
	public KeyAction(DrawCanvas dc)
	{
		this.dc = dc;
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			dc.moveUp = true;
		}
		if(e.getKeyChar() == 'd')
		{
			dc.moveRight = true;
		}
		if(e.getKeyChar() == 's')
		{
			dc.moveDown = true;
		}
		if(e.getKeyChar() == 'a')
		{
			dc.moveLeft = true;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			dc.moveUp = false;
		}
		if(e.getKeyChar() == 'd')
		{
			dc.moveRight = false;
		}
		if(e.getKeyChar() == 's')
		{
			dc.moveDown = false;
		}
		if(e.getKeyChar() == 'a')
		{
			dc.moveLeft = false;
		}
	}
	public void keyTyped(KeyEvent e){}
}
