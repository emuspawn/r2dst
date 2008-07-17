package graphics;

import utilities.Location;

public class Camera
{
	int xover = 0; //how far over the screen has been scrolled horizontally
	int yover = 0; //vertically
	Location location = new Location(0, 0);
	double zoomLevel = 1; //recorded as a percent, 100% is fully zoomed in
	
	public Camera(){}
	public Location getLocation()
	{
		return location;
	}
	public void setLocation(Location l)
	{
		location = l;
	}
	public int getxover()
	{
		return xover;
	}
	public void setxover(int setter)
	{
		xover = setter;
	}
	public void setyover(int setter)
	{
		yover = setter;
	}
	public int getyover()
	{
		return yover;
	}
	public double getZoomLevel()
	{
		return zoomLevel;
	}
	public void setZoomLevel(double setter)
	{
		zoomLevel = setter;
	}
}
