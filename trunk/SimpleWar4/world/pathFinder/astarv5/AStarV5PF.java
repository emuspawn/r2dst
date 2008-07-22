package world.pathFinder.astarv5;

import world.pathFinder.*;
import world.pathFinder.astarv4.*;
import world.unit.*;
import utilities.Location;
import world.World;
import java.awt.Point;

//like AStarV2PF but now accounts for edge tiles and doesnt spit out errors when they are clicked
//

public class AStarV5PF extends PathFinder
{
	World w;
	TileV3[][] tv1;
	int tileLength = 20;
	int matrixWidth; //number of blocks in tv1[][] width
	int matrixHeight; //blocks in height
	
	public AStarV5PF(World w)
	{
		this.w = w;
		setupTileMatrix();
	}
	private void setupTileMatrix()
	{
		matrixWidth = w.getMapWidth()/tileLength;
		matrixHeight = w.getMapHeight()/tileLength;
		tv1 = new TileV3[matrixWidth][matrixHeight];
		for(int i = 0; i < matrixWidth; i++)
		{
			for(int a = 0; a < matrixHeight; a++)
			{
				tv1[i][a] = new TileV3(i, a, tileLength);
			}
		}
	}
	public Path findPath(Location destination, Unit u)
	{
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
		//System.out.println("destination = ("+destination.x+", "+destination.y+")");
		
		
		Path path = new Path();
//		current tile evaluating
		Point cte = new Point((int)(u.getLocation().x / tileLength), (int)(u.getLocation().y / tileLength));
		//destination tile index
		Point dti = new Point((int)((destination.x) / tileLength), (int)((destination.y) / tileLength));
		
		boolean[][] checkedTiles = new boolean[w.getMapWidth()][w.getMapHeight()];
		for(int i = 0; i < w.getMapWidth(); i++)
		{
			for(int a = 0; a < w.getMapHeight(); a++)
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
					if(tbc[i].getFScoreV3(dti, u, checkedTiles) < lfs)
					{
						lfs = tbc[i].getFScoreV3(dti, u, checkedTiles);
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
		//System.out.println("path found and returned");
		return path;
	}
}
