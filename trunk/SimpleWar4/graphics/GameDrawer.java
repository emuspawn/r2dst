package graphics;

import java.awt.*;
import world.controller.*;
import io.ImageLoader;
import world.World;
import world.unit.*;
import ui.menu.Menu;
import utilities.Location;
import world.terrain.*;
import ui.ViewScrollDeterminer;

//draws game and determines screen scrolling

/*
 * determination of screen scrolling is done in here because it is regularly updated by the main thread
 * and it has access to the mouse positon from graphics finder.  Graphics finder was not used because
 * it is not regulaly updated and only changed when the mouse is moved thus not scrolling
 */

public class GameDrawer
{
	GraphicsFinder gf;
	Camera c;
	World w;
	ViewScrollDeterminer vsd;
	boolean drawUnitVisibleBounds = true;
	
	ImageLoader il = new ImageLoader();
	Image[] images;
	
	public GameDrawer(GraphicsFinder gf, World w, Camera c)
	{
		this.gf = gf;
		this.c = c;
		this.w = w;
		vsd = new ViewScrollDeterminer(c, gf.getWidth(), gf.getHeight());
		
		loadGameImages();
	}
	private void loadGameImages()
	{
		images = new Image[10];
		
		images[0] = il.loadImage("simpleWarIntroScreen2.png");
		images[0] = images[0].getScaledInstance(gf.getWidth(), gf.getHeight(), Image.SCALE_SMOOTH);
	}
	public void performGameDrawFunctions()
	{
		Graphics g = gf.getGraphics();
		int xover = c.getxover();
		int yover = c.getyover();
		
		int rm = w.getRunSpecification().getMode(); //run mode
		
		if(rm == 2)
		{
			g.setColor(Color.green);
			g.fillRect(0, 0, gf.getWidth(), gf.getHeight());
			
			drawMapBounds2(g);
			
			drawTerrain(g, xover, yover);
			
			drawUnits(g);
			//drawTileGrid(g);
			drawUnitPaths(g);
			
			drawControllerStartLocations(g);
			
			//scroll test, scrolls the screen if positive
			vsd.testForScreenScroll(gf.mouseLocation);
		}
		else if(rm == 3)
		{
			drawMainScreen(g);
		}
		
		drawGameMenus(g);
		
		gf.getBufferStrategy().show();
		g.dispose();
	}
	private void drawControllerStartLocations(Graphics g)
	{
		Location l;
		ControllerStartLocation[] csl = w.getMap().getControllerStartLocations();
		g.setColor(Color.magenta);
		for(int i = 0; i < csl.length; i++)
		{
			if(csl[i] != null)
			{
				l = c.getVisibleLocation(csl[i].getCastleLocation());
				if(l.x != -1 && l.y != -1)
				{
					g.fillOval((int)l.x-10, (int)l.y-10, 20, 20);
				}
				l = c.getVisibleLocation(csl[i].getDivineAnchorLocation());
				if(l.x != -1 && l.y != -1)
				{
					g.fillOval((int)l.x-10, (int)l.y-10, 20, 20);
				}
			}
		}
	}
	private void drawMainScreen(Graphics g)
	{
		//aka the game setup screen
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, gf.getWidth(), gf.getHeight());
		
		g.drawImage(images[0], 0, 0, null);
	}
	private void drawMapBounds2(Graphics g)
	{
		//draws one point at each corner (if visible)
		g.setColor(Color.black);
		int x = (int)(c.getVisibleLocation(new Location(0, 0)).x);
		int y = (int)(c.getVisibleLocation(new Location(0, 0)).y);
		if(x != -1 && y != -1)
		{
			g.drawRect(x, y, 5, 5);
		}
		x = (int)(c.getVisibleLocation(new Location(w.getMapWidth(), 0)).x);
		y = (int)(c.getVisibleLocation(new Location(w.getMapWidth(), 0)).y);
		if(x != -1 && y != -1)
		{
			g.drawRect(x, y, 5, 5);
		}
		x = (int)(c.getVisibleLocation(new Location(0, w.getMapHeight())).x);
		y = (int)(c.getVisibleLocation(new Location(0, w.getMapHeight())).y);
		if(x != -1 && y != -1)
		{
			g.drawRect(x, y, 5, 5);
		}
		x = (int)(c.getVisibleLocation(new Location(w.getMapWidth(), w.getMapHeight())).x);
		y = (int)(c.getVisibleLocation(new Location(w.getMapWidth(), w.getMapHeight())).y);
		if(x != -1 && y != -1)
		{
			g.drawRect(x, y, 5, 5);
		}
	}
	private void drawTerrain(Graphics g, int xover, int yover)
	{
		Terrain[] t = w.getTerrain();
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] != null)
			{
				t[i].drawTerrain(g, xover, yover);
			}
		}
	}
	private void drawGameMenus(Graphics g)
	{
		Menu[] m = w.getMenuCheckEngine().getMenus();
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				if(m[i].getVisible())
				{
					m[i].drawMenu(g);
					if(m[i].getHeaderClicked())
					{
						Point p = gf.mouseLocation;
						Rectangle header = m[i].getHeaderBounds();
						g.setColor(Color.orange);
						g.drawRect(p.x, p.y, header.width, header.height);
						g.drawRect(p.x+1, p.y+1, header.width-2, header.height-2);
						g.drawRect(p.x+2, p.y+2, header.width-4, header.height-4);
					}
				}
			}
		}
	}
	private void drawUnitPaths(Graphics g)
	{
		Unit[] u = w.getUnitEngine().getUnits();
		Location[] l;
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				if(u[i].getPath() != null)
				{
					l = u[i].getPath().getFullPath();
					for(int a = 0; a < l.length; a++)
					{
						if(l[a] != null)
						{
							int x = (int)(c.getVisibleLocation(l[a]).x);
							int y = (int)(c.getVisibleLocation(l[a]).y);
							g.setColor(Color.red);
							g.fillOval(x, y, 5, 5);
							g.setColor(Color.black);
							g.drawString(""+(a+1), x-8, y+7);
						}
					}
				}
			}
		}
	}
	private void drawTileGrid(Graphics g)
	{
		g.setColor(Color.black);
		for(int i = 0; i < (1280/20); i++)
		{
			g.drawLine(i*20, 0, i*20, 800);
		}
		for(int i = 0; i < (800/20); i++)
		{
			g.drawLine(0, i*20, 1280, i*20);
		}
	}
	private void drawUnits(Graphics g)
	{
		Unit[] u = w.getUnitEngine().getUnits();
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				u[i].drawUnit(g);
				if(drawUnitVisibleBounds)
				{
					u[i].drawUnitVisibleBounds(g);
				}
			}
		}
	}
}
