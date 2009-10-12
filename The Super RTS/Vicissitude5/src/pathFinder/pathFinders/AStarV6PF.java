package pathFinder.pathFinders;

import pathFinder.*;
import utilities.Location;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/*
 * path finds quickly and inefficiently, can make it around basic shapes
 */

public class AStarV6PF extends PathFinder
{
	TileV3[][] tv1;
	int tileLength = 10;
	int matrixWidth; //number of blocks in tv1[][] width
	int matrixHeight; //blocks in height
	
	public AStarV6PF(int mapWidth, int mapHeight)
	{
		super(mapWidth, mapHeight);
	}
	private void setupTileMatrix()
	{
		matrixWidth = mapWidth/tileLength;
		matrixHeight = mapHeight/tileLength;
		tv1 = new TileV3[matrixWidth][matrixHeight];
		for(int i = 0; i < matrixWidth; i++)
		{
			for(int a = 0; a < matrixHeight; a++)
			{
				tv1[i][a] = new TileV3(i, a, tileLength);
			}
		}
	}
	//public Path findPath(Location destination, Unit u)
	public Path findPath(Location destination, Location start, ArrayList<Rectangle> bounds)
	{
		setupTileMatrix();
		
		/*
		 * checks to see if the destination given resides outside of the map bounds
		 * as definded by the tile grid for this path finder and corrects such an occurence
		 * 
		 * ex, the grid is 20X20 and a map size of 605 would have a matrix width
		 * of 30 meaning the last 5 pixels could be clicked but the destination
		 * would reside outside of the matrix bounds
		 */
		
		//System.out.println("destination = ("+destination.x+", "+destination.y+")");
		if(destination.x >= tileLength*matrixWidth)
		{
			destination.x = tileLength*matrixWidth-1;
		}
		if(destination.y >= tileLength*matrixHeight)
		{
			destination.y = tileLength*matrixHeight-1;
		}
		
		//to account for moving off top and left bounds of map
		if(destination.x < 0)
		{
			destination.x = 0;
		}
		if(destination.y < 0)
		{
			destination.y = 0;
		}
		//System.out.println("destination = ("+destination.x+", "+destination.y+")");
		
		
		Path path = new Path();
//		current tile evaluating
		Point cte = new Point((int)(start.x/tileLength), (int)(start.y/tileLength));
		//destination tile index
		Point dti = new Point((int)((destination.x)/tileLength), (int)((destination.y)/tileLength));
		
		boolean[][] checkedTiles = new boolean[mapWidth][mapHeight];
		for(int i = 0; i < mapWidth; i++)
		{
			for(int a = 0; a < mapHeight; a++)
			{
				checkedTiles[i][a] = false;
			}
		}
		
		boolean datp = false; //destination added to path
		TileV3[] tbc = new TileV3[4]; //tiles being checked
		Point[] fsMatrixIndexes = new Point[4]; //f score matrix indexes
		double lfs; //lowest f score
		
		while(!datp)
		{
			/*
			 * used as a base evaluation:
			 * cte.y-1 >= 0 && cte.y+1 < matrixHeight && cte.x-1 >= 0 && cte.x+1 < matrixWidth
			 * 
			 * modified slightly for each test so one problem doesnt null every point
			 */
			//top tile
			if(cte.y-1 >= 0 && cte.y < matrixHeight && cte.x >= 0 && cte.x < matrixWidth)
			{
				tbc[0] = tv1[cte.x][cte.y-1];
			}
			else
			{
				tbc[0] = null;
			}
			//bottom tile
			if(cte.y >= 0 && cte.y+1 < matrixHeight && cte.x >= 0 && cte.x < matrixWidth)
			{
				tbc[1] = tv1[cte.x][cte.y+1];
			}
			else
			{
				tbc[1] = null;
			}
			//left tile
			if(cte.y >= 0 && cte.y < matrixHeight && cte.x-1 >= 0 && cte.x < matrixWidth)
			{
				tbc[2] = tv1[cte.x-1][cte.y];
			}
			else
			{
				tbc[2] = null;
			}
			//right tile
			if(cte.y >= 0 && cte.y < matrixHeight && cte.x >= 0 && cte.x+1 < matrixWidth)
			{
				try
				{
					tbc[3] = tv1[cte.x+1][cte.y];
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("index out of bounds, right tile, "+cte);
					e.printStackTrace();
				}
				
			}
			else
			{
				tbc[3] = null;
			}
			fsMatrixIndexes[0] = new Point(cte.x, cte.y-1);
			fsMatrixIndexes[1] = new Point(cte.x, cte.y+1);
			fsMatrixIndexes[2] = new Point(cte.x-1, cte.y);
			fsMatrixIndexes[3] = new Point(cte.x+1, cte.y);
			lfs = 9999999;
			int lfsIndex = 0;
			for(int i = 0; i < 4; i++)
			{
				if(tbc[i] != null)
				{
					if(tbc[i].getFScoreV3(dti, checkedTiles) < lfs)
					{
						lfs = tbc[i].getFScoreV3(dti, checkedTiles);
						lfsIndex = i;
					}
				}
			}
			path.addLocationToPath(tbc[lfsIndex].getCenter());
			cte = fsMatrixIndexes[lfsIndex];
			checkedTiles[fsMatrixIndexes[lfsIndex].x][fsMatrixIndexes[lfsIndex].y] = true;
			
			if(cte.x == dti.x && cte.y == dti.y)
			{
				datp = true;
			}
		}
		Location[] l = path.getFullPath();
		l[l.length-1] = destination;
		path.setFullPath(l);
		return path;
	}
}
class TileV3
{
	Rectangle bounds;
	int length; //the length of one edge of the square that is the tile
	int type; //the type of terrain the tile overlays
	
	/*
	 * type:
	 * 1=land
	 * 2=water
	 * CURRENTLY NOT USED, TOO GAME SPECIFIC
	 */
	
	public TileV3(int x, int y, int length)
	{
		bounds = new Rectangle(x, y, length, length);
		//the x and y for bounds are not the positions of the square on screen but where it resides in the matrix
		this.length = length;
	}
	/*public void determineTileType(Terrain[] t)
	{
		boolean typeChosen = false;
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] != null)
			{
				if(t[i].getBounds().intersects(bounds))
				{
					type = t[i].getType();
					typeChosen = true;
					break;
				}
			}
		}
		if(!typeChosen)
		{
			type = 1;
		}
	}*/
	public void setTerrainType(int setter)
	{
		type = setter;
	}
	public Location getCenter()
	{
		return new Location(bounds.x*length+(length/2), bounds.y*length+(length/2));
	}
	public double getFScoreV3(Point dti, boolean[][] checkedTiles)
	{
		/*if((type == 1 || type == 2) && type != u.getMovementType())
		{
			//System.out.println("f score = 99999, wrong type");
			return 999;
		}*/
		if(checkedTiles[bounds.x][bounds.y])
		{
			//System.out.println("f score = 99999, already checked tile");
			return 999;
		}
		double term1 = dti.x - bounds.x;
		double term2 = dti.y - bounds.y;
		double distance = Math.sqrt((term1*term1)+(term2*term2));
		//System.out.println("f score = "+distance);
		return distance;
	}
}
