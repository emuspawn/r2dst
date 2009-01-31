package listener;

import java.awt.event.*;
import driver.GameEngine;
import graphics.Camera;

public class KeyAction implements KeyListener
{
	GameEngine ge;
	Camera c;
	
	public KeyAction(GameEngine ge, Camera c)
	{
		this.c = c;
		this.ge = ge;
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			ge.moveUp = true;
		}
		if(e.getKeyChar() == 'd')
		{
			ge.moveRight = true;
		}
		if(e.getKeyChar() == 's')
		{
			ge.moveDown = true;
		}
		if(e.getKeyChar() == 'a')
		{
			ge.moveLeft = true;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			ge.moveUp = false;
		}
		if(e.getKeyChar() == 'd')
		{
			ge.moveRight = false;
		}
		if(e.getKeyChar() == 's')
		{
			ge.moveDown = false;
		}
		if(e.getKeyChar() == 'a')
		{
			ge.moveLeft = false;
		}
	}
	public void keyTyped(KeyEvent e){}
}
