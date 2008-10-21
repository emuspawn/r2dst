package tileSystem;

import java.awt.Graphics;
//import java.awt.Color;
import graphics.Camera;

public class TileSystem
{
	TileTypeRegistry ttr;
	int tileSectionSize = 300; //the length of one edge of a TileSection
	TileSection[][] ts;
	int selectedTileType = -1; //the tile type currently selected
	int mapWidth;
	int mapHeight;
	
	public TileSystem(int mapWidth, int mapHeight)
	{
		ttr = new TileTypeRegistry();
		setupTileSelectionMatrix(mapWidth, mapHeight);
	}
	public void setupTileSelectionMatrix(int mapWidth, int mapHeight)
	{
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		ts = new TileSection[(mapWidth/tileSectionSize)+1][(mapHeight/tileSectionSize)+1];
		for(int i = 0; i < (mapWidth/tileSectionSize+1); i++)
		{
			for(int a = 0; a < (mapHeight/tileSectionSize+1); a++)
			{
				ts[i][a] = new TileSection();
			}
		}
	}
	public void resetTileRegistry()
	{
		ttr = new TileTypeRegistry();
	}
	public TileTypeRegistry getTileTypeRegistry()
	{
		return ttr;
	}
	public int getSelectedTileType()
	{
		return selectedTileType;
	}
	public void setSelectedTileType(int setter)
	{
		selectedTileType = setter;
	}
	public void registerTileType(TileType tt)
	{
		ttr.registerTileType(tt);
	}
	public void registerTileTypes(TileType[] tt)
	{
		for(int i = 0; i < tt.length; i++)
		{
			if(tt[i] != null)
			{
				registerTileType(tt[i]);
			}
		}
	}
	public Tile[] getTiles()
	{
		Tile[] t = new Tile[1];
		for(int i = 0; i < (mapWidth/tileSectionSize+1); i++)
		{
			for(int a = 0; a < (mapHeight/tileSectionSize+1); a++)
			{
				Tile[] temp = ts[i][a].t;
				for(int q = 0; q < temp.length; q++)
				{
					if(temp[q] != null)
					{
						t = addTileToTileArray(t, temp[q]);
						//System.out.println("tile added");
					}
				}
			}
		}
		return t;
	}
	private Tile[] addTileToTileArray(Tile[] t, Tile tile)
	{
		boolean added = false;
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] == null)
			{
				t[i] = tile;
				added = true;
				break;
			}
		}
		if(!added)
		{
			t = enlargeTileArray(t);
			addTileToTileArray(t, tile);
		}
		return t;
	}
	private Tile[] enlargeTileArray(Tile[] t)
	{
		Tile[] temp = new Tile[t.length+1];
		for(int i = 0; i < t.length; i++)
		{
			temp[i] = t[i];
		}
		return temp;
	}
	public void registerTile(Tile t)
	{
		//for adding tiles during loading
		int x = t.getLocation().x/tileSectionSize;
		int y = t.getLocation().y/tileSectionSize;
		ts[x][y].addTile(t);
	}
	public void registerTiles(Tile[] t)
	{
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] != null)
			{
				registerTile(t[i]);
			}
		}
	}
	public void registerTile(int tx, int ty)
	{
		//for adding tiles in an editor
		int x = tx/tileSectionSize;
		int y = ty/tileSectionSize;
		if(ttr.getTileType(selectedTileType) != null)
		{
			ts[x][y].addTile(new Tile(ttr.getTileType(selectedTileType).getType(), tx, ty));
		}
	}
	public void removeTile(int tx, int ty)
	{
		int x = tx/tileSectionSize;
		int y = ty/tileSectionSize;
		ts[x][y].removeTile(tx, ty);
	}
	public void drawTiles(Graphics g, Camera c)
	{
		int x = c.getxover()/tileSectionSize;
		int y = c.getyover()/tileSectionSize;
		for(int i = x; i <= (c.getWidth()/tileSectionSize)+1+x; i++)
		{
			for(int a = y; a <= (c.getHeight()/tileSectionSize)+1+y; a++)
			{
				try
				{
					ts[i][a].drawTiles(g, c, ttr);
					//g.setColor(Color.red);
					//g.drawRect(i*tileSectionSize-c.getxover(), a*tileSectionSize-c.getyover(), tileSectionSize, tileSectionSize);
					//System.out.println("rect drawn");
				}
				catch(ArrayIndexOutOfBoundsException e){} //visible screen on the far right side of the map
			}
		}
	}
}
