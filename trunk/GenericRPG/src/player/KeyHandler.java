package player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import weapons.ShotEngine;
import weapons.shots.Pellet;


public class KeyHandler implements KeyListener
{
	boolean moveUp = false;
	boolean moveRight = false;
	boolean moveDown = false;
	boolean moveLeft = false;
	
	Player p;
	ShotEngine se;
	
	public KeyHandler(Player p, ShotEngine se)
	{
		this.p = p;
		this.se = se;
	}
	
	public void handleKeyPresses()
	{
		int speed = p.getSpeed();
		
		if(moveUp)
		{
			p.move(0, -speed);
		}
		if(moveRight)
		{
			p.move(speed, 0);
		}
		if(moveDown)
		{
			p.move(0, speed);
		}
		if(moveLeft)
		{
			p.move(-speed, 0);
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
		if (e.getKeyChar() == 'w')
		{
			moveUp = true;
		}
		if (e.getKeyChar() == 'd')
		{
			moveRight = true;
		}
		if (e.getKeyChar() == 's')
		{
			moveDown = true;
		}
		if (e.getKeyChar() == 'a')
		{
			moveLeft = true;
		}
		if (e.getKeyChar() == 'i')
		{
			se.addShot(new Pellet(p.x, p.y, 0, -4));
		}	
		if (e.getKeyChar() == 'k')
		{
			se.addShot(new Pellet(p.x, p.y, 0, 4));
		}	
		if (e.getKeyChar() == 'j')
		{
			se.addShot(new Pellet(p.x, p.y, -4, 0));
		}	
		if (e.getKeyChar() == 'l')
		{
			se.addShot(new Pellet(p.x, p.y, 4, 0));
		}	
	}
	
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyChar() == 'w')
		{
			moveUp = false;
		}
		if (e.getKeyChar() == 'd')
		{
			moveRight = false;
		}
		if (e.getKeyChar() == 's')
		{ 
			moveDown = false;
		}
		if (e.getKeyChar() == 'a')
		{
			moveLeft = false;
		}
	}
	
	public void keyTyped(KeyEvent arg0){}
}
