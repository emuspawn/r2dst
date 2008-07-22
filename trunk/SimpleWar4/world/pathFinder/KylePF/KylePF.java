package world.pathFinder.KylePF;

import world.pathFinder.*;
import world.unit.*;
import utilities.Location;
import world.World;
import java.awt.Point;

public class KylePF extends PathFinder
{
	World w;
	int tileLength = 20;
	int[][] tv1;
	

	public KylePF(World w)
	{
		this.w = w;
		tv1 = new int[w.getMapWidth()][w.getMapHeight()];
		for(int i = 0; i < (w.getMapWidth()/tileLength); i++)
		{
			for(int a = 0; a < (w.getMapHeight()/tileLength); a++)
			{
				tv1[i][a] = 1;
			}
		}
		setTiles();
	}

	private void setTiles()
	{
		//wall
		for (int i=15; i<24; i++)
		{
			tv1[20][i] = 2;
		}
	}

	private Point getPointDir(Point p, int dir)
	{
		// This function just gets the point in the direction
		Point end = new Point(p.x,p.y);
		switch (dir)
		{
			case 0:
			end.y = p.y+1;
			break;

			case 1:
			end.y = p.y-1;
			break;

			case 2:
			end.x = p.x+1;
			break;

			case 3:
			end.x = p.x-1;
			break;
		}

		return end;
	}
	
	private double getFScore(Point p, Point dest, boolean[][] checkedTiles)
	{
		// Just like yours >.< I just needed a quick estimate to use.
		double term1 = dest.x - p.x;
		double term2 = dest.y - p.y;
		double fscore = Math.sqrt((term1*term1)+(term2*term2));
		return fscore;
	}

	private Path backTrackPath(Point start, Point dest, int[][] tileDirections)
	{
		//This follows the "arrows" back to the start
		//No diagonals yet
		int nodes = 1;
		Point[] pathPoints = new Point[140];
		boolean done = false;
		Point curPoint;
		Path goodPath = new Path();
		Location loc;
		
		pathPoints[1] = getPointDir(dest, tileDirections[dest.x][dest.y]);

		while (!done)
		{
			if (pathPoints[nodes] == start) {done = false; break;}
			curPoint = pathPoints[nodes];
			nodes++;
			pathPoints[nodes] = getPointDir(curPoint, tileDirections[curPoint.x][curPoint.y]);
		}

		for (int i = nodes; i > 0; i--)
		{
			loc = new Location((pathPoints[i].x*tileLength)+tileLength/2,(pathPoints[i].y*tileLength)+tileLength/2);
			goodPath.addLocationToPath(loc);
		}
		return goodPath;
	}

	public Path findPath(Location destination, Unit u)
	{
		//Declare variables
		Path path = new Path();
		Point curTile = new Point((int)(u.getLocation().x / tileLength), (int)(u.getLocation().y / tileLength));
		Point startTile = curTile;
		Point destTile = new Point((int)(destination.x) / tileLength, (int)(destination.y) / tileLength);
		boolean[][] checkedTiles = new boolean[w.getMapWidth()][w.getMapHeight()];
		int[][] tileDirections = new int[w.getMapWidth()][w.getMapHeight()];
		int a, i, j, lownum;
		double lowscore, tscore;
		boolean foundpath = false;
		for(i = 0; i < w.getMapWidth(); i++)
		{
			for(a = 0; a < w.getMapHeight(); a++)
			{
					checkedTiles[i][a] = false;
					tileDirections[i][a] =-1;
			}
		}
		
		Point[] evalTile = new Point[3];
		
		while (!foundpath) //Basic Loop
		{
			/* No diagonals yet, basic Up/down/left/right
			Once assigned, "arrows" aren't un- or re-assigned. */
			
			//up
			evalTile[0] = new Point(curTile.x, curTile.y-1);
			if (tileDirections[curTile.x][curTile.y-1] == -1) tileDirections[curTile.x][curTile.y-1] = 0;
			//down
			evalTile[1] = new Point(curTile.x, curTile.y+1);
			if (tileDirections[curTile.x][curTile.y+1] == -1) tileDirections[curTile.x][curTile.y+1] = 1;
			//left
			evalTile[2] = new Point(curTile.x-1, curTile.y);
			if (tileDirections[curTile.x-1][curTile.y] == -1) tileDirections[curTile.x-1][curTile.y] = 2;
			//right
			evalTile[3] = new Point(curTile.x+1, curTile.y);
			if (tileDirections[curTile.x+1][curTile.y] == -1) tileDirections[curTile.x+1][curTile.y] = 3;
			
			lowscore = getFScore(evalTile[0], destTile, checkedTiles);
			lownum = 0;
			for (i=1; i<4; i++)
			{
				tscore = getFScore(evalTile[i], destTile, checkedTiles);
				if ((tscore < lowscore) && (tv1[evalTile[i].x][evalTile[i].y] != 2))
				{
					lowscore = tscore;
					lownum = i;
				}
				if (evalTile[i] == destTile)
				{
					lowscore = 0;
					lownum = i;
					foundpath = true;
				}
			}
			curTile = evalTile[lownum];
		}

		path = backTrackPath(startTile, destTile, tileDirections);
		return path;
	}

}



