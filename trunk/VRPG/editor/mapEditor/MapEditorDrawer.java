package editor.mapEditor;

import graphics.*;
import editor.mapEditor.tile.*;
import java.awt.*;

public class MapEditorDrawer
{
	MapEditor me;
	GraphicsFinder gf;
	Camera c;
	
	public MapEditorDrawer(MapEditor me, GraphicsFinder gf)
	{
		this.me = me;
		this.gf = gf;
		this.c = me.getCamera();
	}
	public void performDrawFunctions()
	{
		Graphics g = gf.getGraphics();
		
		drawMap(g, false);
		
		gf.getBufferStrategy().show();
		g.dispose();
	}
	public void drawMap(Graphics g, boolean saving)
	{
		//true if drawing to image to be saved
		g.setColor(Color.green);
		g.fillRect(0, 0, gf.getWidth(), gf.getHeight());
		if(!saving)
		{
			drawTiles(g, c);
			drawGrid(g);
			me.getUICheckEngine().drawUI(g);
			drawSavingScreen(g);
		}
		else
		{
			g.fillRect(0, 0, me.getMapWidth(), me.getMapHeight());
			/*
			 * only for when drawing to tiles, draws regardless because GraphicsFinder
			 * passed is null, xover and yover at 0, 0
			 */
			drawTiles(g, new Camera());
		}
		
	}
	private void drawSavingScreen(Graphics g)
	{
		if(me.getSavingNextIteration() || me.getSavingPicNextIteration())
		{
			g.setColor(Color.gray);
			g.fillRect(gf.getWidth()/2-75, gf.getHeight()/2-50, 150, 100);
			g.setColor(Color.black);
			g.drawRect(gf.getWidth()/2-75, gf.getHeight()/2-50, 150, 100);
			g.drawString("Saving...", gf.getWidth()/2-20, gf.getHeight()/2-5);
		}
	}
	private void drawGrid(Graphics g)
	{
		g.setColor(Color.black);
		Point p1;
		Point p2;
		for(int i = 0; i < me.getMapWidth(); i+=me.getGridWidth())
		{
			p1 = c.getScreenLocation(new Point(i, 0));
			p2 = c.getScreenLocation(new Point(i, me.getMapHeight()));
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
		for(int i = 0; i < me.getMapHeight(); i+=me.getGridHeight())
		{
			p1 = c.getScreenLocation(new Point(0, i));
			p2 = c.getScreenLocation(new Point(me.getMapWidth(), i));
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
		p1 = c.getScreenLocation(new Point(0, 0));
		g.drawRect(p1.x, p1.y, me.getMapWidth(), me.getMapHeight());
	}
	private void drawTiles(Graphics g, Camera c)
	{
		Tile[] t = me.getTiles();
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] != null)
			{
				t[i].drawTile(g, c);
			}
		}
	}
}
