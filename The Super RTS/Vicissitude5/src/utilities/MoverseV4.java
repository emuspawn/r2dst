package utilities;

/**
 * determines a Location along the line specified by (Location)start and (Location)end
 * that is the (double)movement distance from (Location)start, moves to (Location)end
 * if the distance between the start and end is less then the movement parameter, handles
 * 3d as well as 2d
 */

public final class MoverseV4
{
	public static void main(String[] args)
	{
		System.out.println(getNewLocation(new Location(1, 5937, 4), new Location(11332, 33, 483), 10));
	}
	/**
	 * gets a new location along the line formed using both passed points
	 * that is "movement" distance away from the starting point
	 * @param start the starting point
	 * @param end the ending point
	 * @param movement how far along the line formed by the starting and
	 * ending points the new location is
	 * @return returns a new location that is "movement" distance from
	 * the starting point along the line formed by the starting and
	 * ending points
	 */
	public static Location getNewLocation(Location start, Location end, double movement)
	{
		//System.out.println("start = "+start+", end = "+end);
		
		if(start.distanceTo(end) <= movement)
		{
			//System.out.println("in range, returning "+end);
			return end;
		}
		Location ab = new Location(start.x-end.x, start.y-end.y, start.z-end.z);
		//System.out.println("ab = "+ab);
		
		/*double xsquaredLambda = ab.x*ab.x;
		double xnonSquaredLambda = 2*(ab.x*start.x);
		double xconstant = start.x*start.x;
		
		double ysquaredLambda = ab.y*ab.y;
		double ynonSquaredLambda = 2*(ab.y*start.y);
		double yconstant = start.y*start.y;
		
		double zsquaredLambda = ab.z*ab.z;
		double znonSquaredLambda = 2*(ab.z*start.z);
		double zconstant = start.z*start.z;
		
		double distance = movement*movement;
		double squaredLambda = xsquaredLambda+ysquaredLambda+zsquaredLambda;
		System.out.println("s lambda = "+squaredLambda);
		double nonSquaredLambda = xnonSquaredLambda+ynonSquaredLambda+znonSquaredLambda;
		System.out.println("non s lambda = "+nonSquaredLambda);
		double constant = xconstant+yconstant+zconstant-distance;
		System.out.println("constant = "+constant);
		Location roots = getRoots(squaredLambda, nonSquaredLambda, constant);
		roots.x = -roots.x;
		System.out.println("roots = "+roots);
		Location l = new Location(start.x+ab.x*roots.x, start.y+ab.y*roots.y, start.z+ab.z*roots.z);
		System.out.println("dist to = "+l.distanceTo(start));
		return l;*/
		
		double distance = movement*movement;
		double lambda = (ab.x*ab.x)+(ab.y*ab.y)+(ab.z*ab.z);
		lambda = -Math.sqrt(distance / lambda);
		Location l = new Location(start.x+ab.x*lambda, start.y+ab.y*lambda, start.z+ab.z*lambda);
		//System.out.println("dist to = "+l.distanceTo(start));
		return l;
	}
}
