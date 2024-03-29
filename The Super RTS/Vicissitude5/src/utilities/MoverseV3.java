package utilities;

/**
 * determines a Location along the line specified by (Location)start and (Location)end
 * that is the (double)movement distance from (Location)start, moves to (Location)end
 * if the distance between the start and end is less then the movement parameter
 */

public class MoverseV3
{
	private double getDistanceTo(Location to, Location from)
	{
		double term1 = to.x - from.x;
		double term2 = to.y - from.y;
		return Math.sqrt((term1*term1)+(term2*term2));
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
		MoverseV3 mv3 = new MoverseV3();
		//checks to see if the unit is in range of the point its moving to
		double term1 = start.x - end.x;
		double term2 = start.y - end.y;
		double dtnp = mv3.getDistanceTo(start, end);
		
		if(dtnp <= movement)
		{
			return end;
		}
		else
		{
			double xover = Math.abs(term1);
			double yover = Math.abs(term2);
			double angle = Math.atan(yover/xover);
			double x = Math.cos(angle)*(movement);
			double y = Math.sin(angle)*(movement);
			
			if(xover == 0)
			{
				y = movement;
			}
			else if(yover == 0)
			{
				x = movement;
			}
			
			if(start.x < end.x)
			{
				if(start.y < end.y)
				{
					//right, up
					return new Location(start.x+x, start.y+y);
				}
				else if(start.y > end.y)
				{
					//right, down
					return new Location(start.x+x, start.y-y);
				}
				else if(start.y == end.y)
				{
					//right
					return new Location(start.x+x, start.y);
				}
			}
			else if(start.x > end.x)
			{
				if(start.y < end.y)
				{
					//left, up
					return new Location(start.x-x, start.y+y);
				}
				else if(start.y > end.y)
				{
					//left, down
					return new Location(start.x-x, start.y-y);
				}
				else if(start.y == end.y)
				{
					//left
					return new Location(start.x-x, start.y);
				}
			}
			else if(start.x == end.x)
			{
				if(start.y < end.y)
				{
					//up
					return new Location(start.x, start.y+y);
				}
				else if(start.y > end.y)
				{
					//down
					return new Location(start.x, start.y-y);
				}
			}
		}
		return null;
	}
}
