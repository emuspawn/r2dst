package world.pathFinder.astarv2;

import world.pathFinder.*;
import world.unit.*;
import utilities.Location;
import world.World;
import java.awt.Point;

public class AStarV2PF extends PathFinder
{
	World w;
	TileV2[][] tv1;
	int tileLength = 20;
	
	public AStarV2PF(World w)
	{
		this.w = w;
		setupTileMatrix();
	}
	private void setupTileMatrix()
	{
		System.out.println("here 4");
		tv1 = new TileV2[w.getMapWidth()][w.getMapHeight()];
		for(int i = 0; i < (w.getMapWidth()/tileLength); i++)
		{
			for(int a = 0; a < (w.getMapHeight()/tileLength); a++)
			{
				tv1[i][a] = new TileV2(i, a, tileLength);
				//tv1[i][a].determineTileType(w.getTerrain());
			}
		}
		setupTestTiles();
	}
	private void setupTestTiles()
	{
//		solely for testing purposes below, sets some terrain to water
		//1
		tv1[20][15].setTerrainType(2);
		tv1[20][16].setTerrainType(2);
		tv1[20][17].setTerrainType(2);
		tv1[20][18].setTerrainType(2);
		tv1[20][19].setTerrainType(2);
		tv1[20][20].setTerrainType(2);
		tv1[20][21].setTerrainType(2);
		tv1[20][22].setTerrainType(2);
		tv1[20][23].setTerrainType(2);
		
		//2
		tv1[20][6].setTerrainType(2);
		tv1[20][7].setTerrainType(2);
		tv1[20][8].setTerrainType(2);
		tv1[20][9].setTerrainType(2);
		tv1[20][10].setTerrainType(2);
		tv1[20][11].setTerrainType(2);
		tv1[20][12].setTerrainType(2);
		tv1[20][13].setTerrainType(2);
		tv1[20][5].setTerrainType(2);
		
		//3
		tv1[21][5].setTerrainType(2);
		tv1[22][5].setTerrainType(2);
		tv1[23][5].setTerrainType(2);
		tv1[24][5].setTerrainType(2);
		tv1[25][5].setTerrainType(2);
		tv1[26][5].setTerrainType(2);
		tv1[27][5].setTerrainType(2);
		tv1[28][5].setTerrainType(2);
		tv1[29][5].setTerrainType(2);
		
		//4
		tv1[22][7].setTerrainType(2);
		tv1[22][8].setTerrainType(2);
		tv1[22][9].setTerrainType(2);
		tv1[22][10].setTerrainType(2);
		tv1[22][11].setTerrainType(2);
		tv1[22][12].setTerrainType(2);
		tv1[22][13].setTerrainType(2);
		tv1[22][14].setTerrainType(2);
		tv1[22][15].setTerrainType(2);
		
		//5
		tv1[22][17].setTerrainType(2);
		tv1[23][17].setTerrainType(2);
		tv1[24][17].setTerrainType(2);
		tv1[25][17].setTerrainType(2);
		tv1[26][17].setTerrainType(2);
		tv1[27][17].setTerrainType(2);
		tv1[28][17].setTerrainType(2);
		tv1[29][17].setTerrainType(2);
		tv1[30][17].setTerrainType(2);
		
		//6
		tv1[21][25].setTerrainType(2);
		tv1[22][25].setTerrainType(2);
		tv1[23][25].setTerrainType(2);
		tv1[24][25].setTerrainType(2);
		tv1[25][25].setTerrainType(2);
		tv1[26][25].setTerrainType(2);
		tv1[27][25].setTerrainType(2);
		tv1[28][25].setTerrainType(2);
		tv1[29][25].setTerrainType(2);
		
		//7
		tv1[24][22].setTerrainType(2);
		tv1[25][22].setTerrainType(2);
		tv1[26][22].setTerrainType(2);
		tv1[27][22].setTerrainType(2);
		tv1[28][22].setTerrainType(2);
		tv1[29][22].setTerrainType(2);
		tv1[30][22].setTerrainType(2);
		tv1[31][22].setTerrainType(2);
		tv1[32][22].setTerrainType(2);
	}
	public Path findPath(Location destination, Unit u)
		{
			Path path = new Path();
			
			//System.out.println("tileLength = "+tileLength);
			//System.out.println("u.getLocation().x = "+u.getLocation().x);
			//System.out.println("u.getLocation().y = "+u.getLocation().y);
			
	//		current tile evaluating
			Point cte = new Point((int)(u.getLocation().x / tileLength), (int)(u.getLocation().y / tileLength));
			//destination tile index
			Point dti = new Point((int)(destination.x) / tileLength, (int)(destination.y) / tileLength);
			
			//System.out.println("cte = "+cte);
			//System.out.println("dti = "+dti);
			
			//System.out.println();
			//System.out.println();
			
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
				//System.out.println("0 = "+fsMatrixIndexes[0]);
				//System.out.println("1 = "+fsMatrixIndexes[1]);
				//System.out.println("2 = "+fsMatrixIndexes[2]);
				//System.out.println("3 = "+fsMatrixIndexes[3]);
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
				//System.out.println("center = "+tbc[lfsIndex].getCenter().x+", "+tbc[lfsIndex].getCenter().y);
				cte = fsMatrixIndexes[lfsIndex];
				checkedTiles[fsMatrixIndexes[lfsIndex].x][fsMatrixIndexes[lfsIndex].y] = true;
				
				//System.out.println("CHOOSES "+cte);
				//System.out.println();
				
				if(cte.x == dti.x && cte.y == dti.y)
				{
					datp = true;
				}
			}
			
			/*if(path == null)
			{
				System.out.println("path is null");
			}
			else
			{
				System.out.println("path is NOT null");
			}*/
			System.out.println("path found and returned");
			return path;
		}
}
