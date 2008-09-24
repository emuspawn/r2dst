package driver;

import java.awt.Graphics;
import java.awt.image.*;
import utilities.ImageUtil;
import listener.*;
import graphics.*;
import controller.RPGController;
import rpgWorld.*;
import rpgWorld.unit.*;

public class MainThread implements RPGController, Runnable
{
	BufferedImage worldPic;
	GraphicsFinder gf;
	Drawer d;
	Camera c;
	WindowDisplay wd;
	boolean windowed;
	boolean classicMovement; //whether or not the unit moves classically, or angle based
	
	RPGWorld rpgw;
	RPGClientWorld w;
	
	MouseAction ma;
	KeyAction ka;
	MouseMotionAction mma;
	
	//hero movement keys
	boolean up = false;
	boolean right = false;
	boolean down = false;
	boolean left = false;
	
	public MainThread(boolean windowed, boolean classicMovement)
	{
		this.classicMovement = classicMovement;
		ma = new MouseAction(this);
		ka = new KeyAction(this);
		mma = new MouseMotionAction(this);
		worldPic = ImageUtil.loadImage("\\worldPic.png");
		c = new Camera(500, 500);

		rpgw = new RPGWorld();
		w = new RPGClientWorld(rpgw);
		
		d = new Drawer(this, c);
		setWindowed(windowed);
		
		Thread runner = new Thread(this);
		runner.start();
	}
	public void setWindowed(boolean setter)
	{
		windowed = setter;
		if(windowed)
		{
			wd = new WindowDisplay(ma, ka, mma, c, d);
		}
		else
		{
			gf = new GraphicsFinder(ma, ka, mma);
		}
	}
	public Camera getCamera()
	{
		return c;
	}
	public BufferedImage getWorldPic()
	{
		return worldPic;
	}
	public RPGClientWorld getRPGClientWorld()
	{
		return w;
	}
	public void run()
	{
		
		for(;;)
		{
			drawGame();
			moveHero();
			c.centerOn(w.getHero().getLocation().getPoint());
			//c.centerOn(w.getHero().getLocation().getPoint());
			//System.out.println("hero location = "+w.getHero().getLocation());
			//System.out.println("camera xover, yover = "+c.getxover()+", "+c.getyover());
			//System.out.println();
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException e){}
		}
	}
	private void moveHero()
	{
		if(!classicMovement)
		{
			if(up)
			{
				w.getHero().moveUnit();
			}
			if(right)
			{
				w.getHero().rotatePositive();
			}
			if(left)
			{
				w.getHero().rotateNegative();
			}
		}
		else
		{
			if(up)
			{
				w.getHero().moveUnit(Unit.UP);
			}
			if(right)
			{
				w.getHero().moveUnit(Unit.RIGHT);
			}
			if(down)
			{
				w.getHero().moveUnit(Unit.DOWN);
			}
			if(left)
			{
				w.getHero().moveUnit(Unit.LEFT);
			}
		}
	}
	private void drawGame()
	{
		if(!windowed)
		{
			Graphics g = gf.getGraphics();
			d.drawGame(g, gf.getWidth(), gf.getHeight());
			gf.getBufferStrategy().show();
			g.dispose();
		}
		else
		{
			wd.getDrawCanvas().repaint();
		}
	}
	public static void main(String[] args)
	{
		new MainThread(true, false);
	}
	public void setUp(boolean setter)
	{
		up = setter;
	}
	public void setRight(boolean setter)
	{
		right = setter;
	}
	public void setDown(boolean setter)
	{
		down = setter;
	}
	public void setLeft(boolean setter)
	{
		left = setter;
	}
}
