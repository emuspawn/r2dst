package graphics;

import java.awt.*;
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
	
	public GameDrawer(GraphicsFinder gf, World w, Camera c)
	{
		this.gf = gf;
		this.c = c;
		this.w = w;
		vsd = new ViewScrollDeterminer(c, gf.getWidth(), gf.getHeight());
	}
	public void performGameDrawFunctions()
	{
		Graphics g = gf.getGraphics();
		int xover = c.getxover();
		int yover = c.getyover();
		double zoomLevel = c.getZoomLevel();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, gf.getWidth(), gf.getHeight());
		
		drawGrass(g, xover, yover);
		
		drawTerrain(g, xover, yover);
		
		drawUnits(g, xover, yover, zoomLevel);
		drawTileGrid(g);
		drawDebugWater(g);
		drawUnitPaths(g);
		
		
		drawGameMenus(g);
		
		
		//scroll test
		vsd.testForScreenScroll(gf.mouseLocation);
		
		gf.getBufferStrategy().show();
		g.dispose();
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
	private void drawDebugWater(Graphics g)
	{
		g.setColor(Color.blue);
		g.fillRect(400, 300, 20, 180);
		g.fillRect(400, 100, 20, 180);
		g.fillRect(420, 100, 180, 20);
		g.fillRect(440, 140, 20, 180);
		g.fillRect(440, 340, 180, 20);
		g.fillRect(420, 500, 180, 20);
		g.fillRect(480, 440, 180, 20);
		
		g.setColor(Color.black);
		g.drawString("1", 400, 320);
		g.drawString("2", 400, 120);
		g.drawString("3", 420, 120);
		g.drawString("4", 440, 160);
		g.drawString("5", 440, 360);
		g.drawString("6", 420, 520);
		g.drawString("7", 480, 460);
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
							g.setColor(Color.red);
							g.fillOval((int)l[a].x, (int)l[a].y, 5, 5);
							g.setColor(Color.black);
							g.drawString(""+(a+1), (int)l[a].x-8, (int)l[a].y+7);
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
	private void drawUnits(Graphics g, int xover, int yover, double zoomLevel)
	{
		Unit[] u = w.getUnitEngine().getUnits();
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				u[i].drawUnit(g);
			}
		}
	}
	private void drawGrass(Graphics g, int xover, int yover)
	{
		g.setColor(Color.green);
		g.fillRect(0-xover, 0-yover, 500, 500);
	}
}
