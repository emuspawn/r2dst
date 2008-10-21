package tileSystem;

import java.awt.Graphics;
import java.awt.Point;
import graphics.Camera;

public class TileSection
{
	Tile[] t = new Tile[1];
	
	public TileSection(){}
	public void addTile(Tile tile)
	{
		boolean added = false;
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] == null)
			{
				boolean tileExists = checkTileExists(tile.getLocation());
				if(!tileExists)
				{
					t[i] = tile;
					//System.out.println("tile added");
					added = true;
					break;
				}
				else
				{
					added = true;
					break;
				}
			}
		}
		if(!added)
		{
			enlargeTileArray();
			addTile(tile);
		}
	}
	public void removeTile(int x, int y)
	{
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] != null)
			{
				if(t[i].getLocation().x == x && t[i].getLocation().y == y)
				{
					t[i] = null;
					break;
				}
			}
		}
	}
	private boolean checkTileExists(Point p)
	{
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] != null)
			{
				if(t[i].getLocation().x == p.x && t[i].getLocation().y == p.y)
				{
					return true;
				}
			}
		}
		return false;
	}
	private void enlargeTileArray()
	{
		Tile[] temp = new Tile[t.length+1];
		for(int i = 0; i < t.length; i++)
		{
			temp[i] = t[i];
		}
		t = temp;
	}
	public void drawTiles(Graphics g, Camera c, TileTypeRegistry ttr)
	{
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] != null)
			{
				if(c.getOnScreen(t[i].getBounds()))
				{
					t[i].drawTile(g, c, ttr);
				}
			}
		}
	}
	
}
