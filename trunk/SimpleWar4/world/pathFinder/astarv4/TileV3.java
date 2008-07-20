package world.pathFinder.astarv4;

import utilities.Location;
import world.terrain.*;
import java.awt.Rectangle;
import world.unit.*;
import java.awt.Point;

public class TileV3
{
	Rectangle bounds;
	int length; //the length of one edge of the square that is the tile
	int type; //the type of terrain the tile overlays
	
	/*
	 * type:
	 * 1=land
	 * 2=water
	 */
	
	public TileV3(int x, int y, int length)
	{
		bounds = new Rectangle(x, y, length, length);
		//the x and y for bounds are not the positions of the square on screen but where it resides in the matrix
		this.length = length;
	}
	public void determineTileType(Terrain[] t)
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
	}
	public void setTerrainType(int setter)
	{
		type = setter;
	}
	public Location getCenter()
	{
		return new Location(bounds.x*length+(length/2), bounds.y*length+(length/2));
	}
	public double getFScoreV3(Point dti, Unit u, boolean[][] checkedTiles)
	{
		if((type == 1 || type == 2) && type != u.getMovementType())
		{
			//System.out.println("f score = 99999, wrong type");
			return 999;
		}
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
