package world.pathFinder.astarv3;

import world.pathFinder.*;
import world.pathFinder.astarv2.*;
import world.unit.*;
import utilities.Location;
import world.World;
import java.awt.Point;

//like AStarV2PF but not accounts for edge tiles

public class AStarV3PF extends PathFinder
{
	World w;
	TileV2[][] tv1;
	int tileLength = 20;
	int matrixWidth; //number of blocks in tv1[][] width
	int matrixHeight; //blocks in height
	
	public AStarV3PF(World w)
	{
		this.w = w;
		setupTileMatrix();
	}
	private void setupTileMatrix()
	{
		matrixWidth = w.getMapWidth()/tileLength;
		matrixHeight = w.getMapHeight()/tileLength;
		tv1 = new TileV2[matrixWidth][matrixHeight];
		for(int i = 0; i < matrixWidth; i++)
		{
			for(int a = 0; a < matrixHeight; a++)
			{
				tv1[i][a] = new TileV2(i, a, tileLength);
			}
		}
	}
	public Path findPath(Location destination, Unit u)
		{
			Path path = new Path();
	//		current tile evaluating
			Point cte = new Point((int)(u.getLocation().x / tileLength), (int)(u.getLocation().y / tileLength));
			//destination tile index
			Point dti = new Point((int)(destination.x) / tileLength, (int)(destination.y) / tileLength);
			
			boolean[][] checkedTiles = new boolean[w.getMapWidth()][w.getMapHeight()];
			for(int i = 0; i < w.getMapWidth(); i++)
			{
				for(int a = 0; a < w.getMapHeight(); a++)
				{
					checkedTiles[i][a] = false;
				}
			}
			
			boolean datp = false; //destination added to path
			TileV2[] tbc = new TileV2[4]; //tiles being checked
			Point[] fsMatrixIndexes = new Point[4]; //f score matrix indexes
			double lfs; //lowest f score
			while(!datp)
			{
				//top tile
				if(cte.y-1 >= 0)
				{
					tbc[0] = tv1[cte.x][cte.y-1];
				}
				else
				{
					tbc[0] = null;
				}
				//bottom tile
				if(cte.y+1 <= matrixHeight)
				{
					tbc[1] = tv1[cte.x][cte.y+1];
				}
				else
				{
					tbc[1] = null;
				}
				//left tile
				if(cte.x-1 >= 0)
				{
					tbc[2] = tv1[cte.x-1][cte.y];
				}
				else
				{
					tbc[2] = null;
				}
				//right tile
				if(cte.x+1 <= matrixWidth)
				{
					tbc[3] = tv1[cte.x+1][cte.y];
				}
				else
				{
					tbc[3] = null;
				}
				fsMatrixIndexes[0] = new Point(cte.x, cte.y-1);
				fsMatrixIndexes[1] = new Point(cte.x, cte.y+1);
				fsMatrixIndexes[2] = new Point(cte.x-1, cte.y);
				fsMatrixIndexes[3] = new Point(cte.x+1, cte.y);
				lfs = 999999;
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
			System.out.println("path found and returned");
			return path;
		}
}
