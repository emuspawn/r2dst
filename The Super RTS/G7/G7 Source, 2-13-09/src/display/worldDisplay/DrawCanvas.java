package display.worldDisplay;

import graphics.Camera;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import world.World;
import world.shot.Shot;
import driver.GameEngine;
import java.awt.event.*;

public class DrawCanvas extends JPanel implements Runnable, FocusListener
{
	private final static long serialVersionUID = 4;
	
	World w;
	Camera c;
	Frame owner;
	GameEngine ge;
	
	double fps;

	public boolean moveUp = false;
	public boolean moveRight = false;
	public boolean moveDown = false;
	public boolean moveLeft = false;
	
	public DrawCanvas(World w, Frame owner)
	{
		setSize(owner.getWidth(), owner.getHeight());
		this.w = w;
		c = new Camera(0, 0);
		this.owner = owner;
		addFocusListener(this);
		
		new Thread(this).start();
	}
	private void updateCamera()
	{
		c.setWidth(getWidth());
		c.setHeight(getHeight());
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
	public void update(Graphics g)
	{
		paint(g);
	}
	public void run()
	{
		double total = 0;
		int fpsCount = 0;
		int maxfpsCount = 150;
		for(;;)
		{
			double start = System.currentTimeMillis();
			updateCamera();
			repaint();
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException e){}
			double end = System.currentTimeMillis();
			fpsCount++;
			total += (int)(1/((end-start)/1000));
			fps = total/fpsCount;
			if(fpsCount == maxfpsCount)
			{
				fpsCount = 0;
				total = 0;
			}
		}
	}
	public void paint(Graphics g)
	{
		/*Graphics2D g2 = (Graphics2D)g;
		Image i = createVolatileImage(getWidth(), getHeight());
		drawOffScreen((Graphics2D)i.getGraphics());
		g2.drawImage(i, 0, 0, null);*/
		
		Graphics2D g2 = (Graphics2D)g;
		drawOffScreen(g2);
	}
	private void drawOffScreen(Graphics2D g)
	{
		g.setColor(Color.green);
		g.fillRect(0, 0, getWidth(), getHeight());
		ArrayList<Shot> s = w.getShotEngine().getShots();
		for(int i = 0; i < s.size(); i++)
		{
			s.get(i).drawShot(g, c);
		}
		g.setColor(Color.cyan);
		Point p = c.getScreenLocation(new Point(w.getWidth()/2-10, w.getHeight()/2-10));
		g.fillRect(p.x, p.y, 20, 20);
		w.drawWorld(g, c);
		g.setColor(Color.black);
		g.drawRect(0-c.getxover(), 0-c.getyover(), w.getWidth(), w.getHeight());
		g.drawString("FPS: "+fps, 3, 16);
	}
	public void focusGained(FocusEvent e)
	{
		owner.requestFocus();
	}
	public void focusLost(FocusEvent e){}
}