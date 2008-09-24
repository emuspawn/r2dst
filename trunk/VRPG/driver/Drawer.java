package driver;

import graphics.*;
import rpgWorld.RPGClientWorld;
import java.awt.*;
import java.awt.image.*;

public class Drawer
{
	RPGClientWorld w;
	BufferedImage worldPic;
	Camera c;
	
	public Drawer(MainThread mt, Camera c)
	{
		this.c = c;
		worldPic = mt.getWorldPic();
		w = mt.getRPGClientWorld();
	}
	public void drawGame(Graphics g, int width, int height)
	{
		//for drawing in full sreen mode
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		drawGame(g);
	}
	public void drawGame(Graphics g)
	{
		drawWorldImage(g);
		
		//g.setColor(new Color(200, 0, 0, 0));
		//g.fillRect(0, 0, 100, 100);
		
		w.getHero().drawUnit(g, c);
	}
	private void drawWorldImage(Graphics g)
	{
		try
		{
			BufferedImage bi = worldPic.getSubimage(c.getxover(), c.getyover(), c.getWidth(), c.getHeight());
			g.drawImage(bi, 0, 0, null);
		}
		catch(RasterFormatException e)
		{
			int x = 0;
			int width = c.getWidth();
			int xover = c.getxover();
			if(c.getxover() < 0)
			{
				x = 0-c.getxover();
				width = c.getxover()+c.getWidth();
				if(x+width > worldPic.getWidth())
				{
					width = worldPic.getWidth();
				}
				xover = 0;
			}
			else if(c.getxover() > worldPic.getWidth()-c.getWidth())
			{
				x = 0;
				width = worldPic.getWidth() - c.getxover();
			}
			int y = 0;
			int height = c.getHeight();
			int yover = c.getyover();
			if(c.getyover() < 0)
			{
				y = 0-c.getyover();
				height = c.getyover()+c.getHeight();
				if(y+height > worldPic.getHeight())
				{
					height = worldPic.getHeight();
				}
				yover = 0;
			}
			else if(c.getyover() > worldPic.getHeight()-c.getHeight())
			{
				y = 0;
				height = worldPic.getHeight() - c.getyover();
			}
			BufferedImage bi = worldPic.getSubimage(xover, yover, width, height);
			g.drawImage(bi, x, y, null);
		}
	}
}
