package editor.modelEditor;

import java.awt.event.*;

import graphics.GLCamera;

public class KeyActionListener implements KeyListener
{
	private boolean moveUp = false;
	private boolean moveRight = false;
	private boolean moveDown = false;
	private boolean moveLeft = false;
	
	private boolean rotateRight = false;
	private boolean rotateLeft = false;
	
	private boolean zoomIn = false;
	private boolean zoomOut = false;
	
	private boolean lookUp = false;
	private boolean lookDown = false;
	
	public void updateCamera(GLCamera c)
	{
		double scrollAmount = .03*c.getLocation().y;
		double zoomAmount = .01*c.getLocation().y;
		double rotateAmount = 2.4; //degrees
		double lookAmount = .09; //look up/down
		
		if(moveUp)
		{
			c.translate(0, 0, -scrollAmount);
		}
		if(moveRight)
		{
			c.translate(scrollAmount, 0, 0);
		}
		if(moveDown)
		{
			c.translate(0, 0, scrollAmount);
		}
		if(moveLeft)
		{
			c.translate(-scrollAmount, 0, 0);
		}
		if(zoomIn)
		{
			c.zoom(-zoomAmount);
		}
		if(zoomOut)
		{
			c.zoom(zoomAmount);
		}
		if(rotateLeft)
		{
			c.rotate(rotateAmount);
		}
		if(rotateRight)
		{
			c.rotate(-rotateAmount);
		}
		if(lookUp)
		{
			c.getViewLocation().y+=lookAmount;
		}
		if(lookDown)
		{
			c.getViewLocation().y-=lookAmount;
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
		if(e.getKeyChar() == 'e')
		{
			rotateRight = true;
		}
		if(e.getKeyChar() == 'q')
		{
			rotateLeft = true;
		}
		if(e.getKeyChar() == 'r')
		{
			zoomIn = true;
		}
		if(e.getKeyChar() == 'f')
		{
			zoomOut = true;
		}
		if(e.getKeyChar() == 't')
		{
			lookUp = true;
		}
		if(e.getKeyChar() == 'g')
		{
			lookDown = true;
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
		if(e.getKeyChar() == 'e')
		{
			rotateRight = false;
		}
		if(e.getKeyChar() == 'q')
		{
			rotateLeft = false;
		}
		if(e.getKeyChar() == 'r')
		{
			zoomIn = false;
		}
		if(e.getKeyChar() == 'f')
		{
			zoomOut = false;
		}
		if(e.getKeyChar() == 't')
		{
			lookUp = false;
		}
		if(e.getKeyChar() == 'g')
		{
			lookDown = false;
		}
	}
	public void keyTyped(KeyEvent arg0){}
}
