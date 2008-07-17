package world.pathFinder.astarv1;

import world.pathFinder.*;
import world.unit.*;
import utilities.Location;
import world.World;
import java.awt.Point;

public class AStarV1PF extends PathFinder
{
	World w;
	TileV1[][] tv1;
	int tileLength = 20;
	
	public AStarV1PF(World w)
	{
		this.w = w;
		setupTileMatrix();
	}
	private void setupTileMatrix()
	{
		System.out.println("here 4");
		tv1 = new TileV1[w.getMapWidth()][w.getMapHeight()];
		for(int i = 0; i < (w.getMapWidth()/tileLength); i++)
		{
			for(int a = 0; a < (w.getMapHeight()/tileLength); a++)
			{
				tv1[i][a] = new TileV1(i, a, tileLength);
				//tv1[i][a].determineTileType(w.getTerrain());
			}
		}
		//solely for testing purposes below, sets some terrain to water
		tv1[20][15].setTerrainType(2);
		tv1[20][16].setTerrainType(2);
		tv1[20][17].setTerrainType(2);
		tv1[20][18].setTerrainType(2);
		tv1[20][19].setTerrainType(2);
		tv1[20][20].setTerrainType(2);
		tv1[20][21].setTerrainType(2);
		tv1[20][22].setTerrainType(2);
		tv1[20][23].setTerrainType(2);
	}
	public Path findPath(Location destination, Unit u)
		{
			Path path = new Path();
			
			System.out.println("tileLength = "+tileLength);
			System.out.println("u.getLocation().x = "+u.getLocation().x);
			System.out.println("u.getLocation().y = "+u.getLocation().y);
			
	//		current tile evaluating
			Point cte = new Point((int)(u.getLocation().x / tileLength), (int)(u.getLocation().y / tileLength));
			//destination tile index
			Point dti = new Point((int)(destination.x) / tileLength, (int)(destination.y) / tileLength);
			
			System.out.println("cte = "+cte);
			System.out.println("dti = "+dti);
			
			System.out.println();
			System.out.println();
			
			boolean[][] checkedTiles = new boolean[w.getMapWidth()][w.getMapHeight()];
			for(int i = 0; i < w.getMapWidth(); i++)
			{
				for(int a = 0; a < w.getMapHeight(); a++)
				{
					checkedTiles[i][a] = false;
				}
			}
			
			boolean datp = false; //destination added to path
			TileV1[] tbc = new TileV1[4]; //tiles being checked
			Point[] fsMatrixIndexes = new Point[4]; //f score matrix indexes
			int lfs; //lowest f score
			while(!datp)
			{
				tbc[0] = tv1[cte.x][cte.y-1];
				tbc[1] = tv1[cte.x][cte.y+1];
				tbc[2] = tv1[cte.x-1][cte.y];
				tbc[3] = tv1[cte.x+1][cte.y];
				fsMatrixIndexes[0] = new Point(cte.x, cte.y-1);
				fsMatrixIndexes[1] = new Point(cte.x, cte.y+1);
				fsMatrixIndexes[2] = new Point(cte.x-1, cte.y);
				fsMatrixIndexes[3] = new Point(cte.x+1, cte.y);
				lfs = tbc[0].getFScoreV3(dti, u, checkedTiles);
				int lfsIndex = 0;
				System.out.println("0 = "+fsMatrixIndexes[0]);
				System.out.println("1 = "+fsMatrixIndexes[1]);
				System.out.println("2 = "+fsMatrixIndexes[2]);
				System.out.println("3 = "+fsMatrixIndexes[3]);
				for(int i = 1; i < 4; i++)
				{
					//System.out.println("i = "+i);
					if(tbc[i].getFScoreV3(dti, u, checkedTiles) < lfs)
					{
						lfs = tbc[i].getFScoreV3(dti, u, checkedTiles);
						lfsIndex = i;
					}
				}
				
				path.addLocationToPath(tbc[lfsIndex].getCenter());
				System.out.println("center = "+tbc[lfsIndex].getCenter().x+", "+tbc[lfsIndex].getCenter().y);
				cte = fsMatrixIndexes[lfsIndex];
				checkedTiles[fsMatrixIndexes[lfsIndex].x][fsMatrixIndexes[lfsIndex].y] = true;
				
				System.out.println("CHOOSES "+cte);
				System.out.println();
				
				if(cte.x == dti.x && cte.y == dti.y)
				{
					datp = true;
				}
			}
			
			if(path == null)
			{
				System.out.println("path is null");
			}
			else
			{
				System.out.println("path is NOT null");
			}
			System.out.println("path found and returned");
			return path;
		}
}
