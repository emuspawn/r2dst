package dynamicMap3D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import graphics.GLCamera;
import javax.media.opengl.*;

import ui.GLFrame;
import utilities.Location;

/**
 * provides a visual representation of a dynamic map
 * 
 * blue flags mean there are no units in that partition
 * red flags mean there are units
 * 
 * @author Jack
 *
 */
public class MapDisplay implements GLEventListener, KeyListener
{
	GLCamera c;
	DynamicMap3D m;
	GLFrame f;
	
	//for camera movement
	boolean moveForward = false;
	boolean moveRight = false;
	boolean moveBackwards = false;
	boolean moveLeft = false;
	
	boolean rotateRight = false;
	boolean rotateLeft = false;
	
	boolean moveUp = false;
	boolean moveDown = false;
	
	public MapDisplay(GLCamera c)
	{
		this.c = c;
		f = new GLFrame(this);
		f.getGLCanvas().addKeyListener(this);
	}
	public void updateMapDisplay()
	{
		c.setSize(f.getWidth(), f.getHeight());
		updateCamera(c);
	}
	public static void main(String[] args)
	{
		GLCamera c = new GLCamera(new Location(0, 10, 0), new Location(0, 0, -15), 0, 0);
		MapDisplay md = new MapDisplay(c);
		DynamicMap3D dm3 = new DynamicMap3D(new Location(0, 0, 0), 400, 100, 400);
		dm3.setPartitionSize(50);
		md.setMap(dm3);
		for(;;)
		{
			c.setSize(md.f.getWidth(), md.f.getHeight());
			md.updateCamera(c);
			try
			{
				Thread.sleep(30);
			}
			catch(InterruptedException e){}
		}
	}
	public void setMap(DynamicMap3D dm3)
	{
		m = dm3;
	}
	public void display(GLAutoDrawable d)
	{
		GL gl = d.getGL();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		c.loadMatrices(gl);
		
		if(m != null)
		{
			m.drawMap(gl, c);
		}
		
		c.updateCamera(gl);
	}
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2){}
	public void init(GLAutoDrawable d)
	{
		GL gl = d.getGL();
		gl.glEnable(GL.GL_DEPTH_TEST);
	}
	public void reshape(GLAutoDrawable d, int arg1, int arg2, int width, int height)
	{
		GL gl = d.getGL();
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustum(-1, 1, -1, 1, 1, 7000);
		c.setSize(width, height);
	}
	private void updateCamera(GLCamera c)
	{
		double scrollAmount = .6;
		double rotateAmount = 2; //degrees
		
		if(moveForward)
		{
			c.translate(0, 0, -scrollAmount);
		}
		if(moveRight)
		{
			c.translate(scrollAmount, 0, 0);
		}
		if(moveBackwards)
		{
			c.translate(0, 0, scrollAmount);
		}
		if(moveLeft)
		{
			c.translate(-scrollAmount, 0, 0);
		}
		if(moveUp)
		{
			c.translate(0, scrollAmount, 0);
		}
		if(moveDown)
		{
			c.translate(0, -scrollAmount, 0);
		}
		if(rotateLeft)
		{
			c.rotate(rotateAmount);
		}
		if(rotateRight)
		{
			c.rotate(-rotateAmount);
		}
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			moveForward = true;
		}
		if(e.getKeyChar() == 'd')
		{
			moveRight = true;
		}
		if(e.getKeyChar() == 's')
		{
			moveBackwards = true;
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
			moveUp = true;
		}
		if(e.getKeyChar() == 'f')
		{
			moveDown = true;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			moveForward = false;
		}
		if(e.getKeyChar() == 'd')
		{
			moveRight = false;
		}
		if(e.getKeyChar() == 's')
		{
			moveBackwards = false;
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
			moveUp = false;
		}
		if(e.getKeyChar() == 'f')
		{
			moveDown = false;
		}
	}
	public void keyTyped(KeyEvent arg0){}
}
