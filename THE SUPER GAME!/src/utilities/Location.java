package utilities;

import java.awt.Point;
import java.io.Serializable;

public class Location implements Serializable {
	private static final long serialVersionUID = 1L;
	public double x, y;
	
	public Location(Point p)
	{
		this.x = p.x;
		this.y = p.y;
	}
	public Location(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
}
