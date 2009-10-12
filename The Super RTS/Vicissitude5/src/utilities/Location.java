package utilities;

import java.awt.Point;
import java.util.Comparator;

public class Location implements Comparable<Location>
{
	public double x;
	public double y;
	public double z;
	
	public Location(double x, double y)
	{
		this.x = x;
		this.y = y;
		z = 0;
	}
	public Location(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Location(Point p)
	{
		x = p.x;
		y = p.y;
	}
	public int compareTo(Location l)
	{
		if(l.x == x && l.y == y && l.z == z)
		{
			return 0;
		}
		else if(l.x > x)
		{
			return 1;
		}
		return -1;
	}
	/**
	 * translates the location by the passed values
	 * @param xover x translation amount
	 * @param yover y translation amount
	 * @param zover z translation amount
	 */
	public void translate(double xover, double yover, double zover)
	{
		x+=xover;
		y+=yover;
		z+=zover;
	}
	/**
	 * translates the location by the passed values
	 * @param xover x translation amount
	 * @param yover y translation amount
	 */
	public void translate(double xover, double yover)
	{
		translate(xover, yover, 0);
	}
	/**
	 * @return returns the Point equivalent of the location
	 */
	public Point getPoint()
	{
		return new Point((int)x, (int)y);
	}
	public String toString()
	{
		return new String("("+x+", "+y+", "+z+")");
	}
	public double distanceTo(Location end)
	{
		return Math.sqrt(Math.pow(x-end.x, 2)+Math.pow(y-end.y, 2)+Math.pow(z-end.z, 2));
	}
	public static LocationComparator getXComparator()
	{
		//returns a comparator for sorting the x values
		return new LocationComparator(true);
	}
	public static LocationComparator getYComparator()
	{
		//returns a comparator for sorting the x values
		return new LocationComparator(false);
	}
}
class LocationComparator implements Comparator<Location>
{
	boolean x; //sorts by x coordinates if true, y if false
	public LocationComparator(boolean x)
	{
		this.x = x;
	}
	public int compare(Location l1, Location l2)
	{
		if(x)
		{
			if(l1.x > l2.x)
			{
				return 1;
			}
			else if(l2.x > l1.x)
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			if(l1.y > l2.y)
			{
				return 1;
			}
			else if(l2.y > l1.y)
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
	}
}
