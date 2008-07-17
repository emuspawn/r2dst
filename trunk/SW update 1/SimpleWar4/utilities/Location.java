package utilities;

import java.awt.Point;

public class Location
{
	public double x;
	public double y;
	
	public Location(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public Location(Point p)
	{
		x = p.x;
		y = p.y;
	}
}