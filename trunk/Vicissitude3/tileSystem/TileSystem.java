package tileSystem;

import java.awt.Graphics;
import graphics.Camera;

public class TileSystem
{
	TileTypeRegistry ttr;
	int tileSectionSize = 200; //the length of one edge of a TileSection
	TileSection[][] ts;
	int selectedTileType = -1; //the tile type currently selected
	
	public TileSystem(int mapWidth, int mapHeight)
	{
		ttr = new TileTypeRegistry();
		setupTileSelectionMatrix(mapWidth, mapHeight);
	}
	public void setupTileSelectionMatrix(int mapWidth, int mapHeight)
	{
		ts = new TileSection[(mapWidth/tileSectionSize)+1][(mapHeight/tileSectionSize)+1];
		for(int i = 0; i < (mapWidth/tileSectionSize+1); i++)
		{
			for(int a = 0; a < (mapHeight/tileSectionSize+1); a++)
			{
				ts[i][a] = new TileSection();
			}
		}
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
	public void registerTile(Tile t)
	{
		//for adding tiles during loading
		int x = t.getLocation().x/tileSectionSize;
		int y = t.getLocation().y/tileSectionSize;
		ts[x][y].addTile(t);
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
		for(int i = x; i < (c.getWidth()/tileSectionSize)+1+x; i++)
		{
			for(int a = y; a < (c.getHeight()/tileSectionSize)+1+y; a++)
			{
				try
				{
					ts[i][a].drawTiles(g, c, ttr);
					//g.setColor(Color.red);
					//g.drawRect(i*tileSectionSize, a*tileSectionSize, tileSectionSize, tileSectionSize);
					//System.out.println("rect drawn");
				}
				catch(ArrayIndexOutOfBoundsException e){} //visible screen on the far right side of the map
			}
		}
	}
}
