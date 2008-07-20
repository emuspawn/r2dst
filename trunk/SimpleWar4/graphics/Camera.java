package graphics;

import utilities.Location;
import java.awt.Rectangle;
import java.awt.Point;

public class Camera
{
	int xover = 0; //how far over the screen has been scrolled horizontally
	int yover = 0; //vertically
	Location location = new Location(0, 0);
	double zoomLevel = 1; //recorded as a percent, 100% is fully zoomed in
	double screenWidth;
	double screenHeight;
	double dsw; //displayed screen width
	double dsh; //displayed screen height
	
	public Camera(){}
	public void setActualScreenDimensions(int screenWidth, int screenHeight)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		dsw = screenWidth;
		dsh = screenHeight;
	}
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
		double diff = 1 - zoomLevel;
		Location center = new Location((dsw/2)+xover, (dsh/2)+yover);
		
		dsw = screenWidth+(screenWidth*diff);
		dsh = screenHeight+(screenHeight*diff);
		
		xover = (int)(center.x-(dsw/2));
		yover = (int)(center.y-(dsh/2));
	}
	public Point getVirtualPoint(Point p)
	{
		//returns the virtual point in game of a mouse click
		double xoverPercent = p.x/screenWidth;
		double yoverPercent = p.y/screenHeight;
		double x = xover+(xoverPercent*dsw);
		double y = yover+(yoverPercent*dsh);
		return new Point((int)x, (int)y);
	}
	public Location getVisibleLocation(Location l)
	{
		//returns the location of where the Location in virtual spcae should be on the screen, (-1, -1) if offscreen
		Rectangle sBounds = new Rectangle(xover, yover, (int)dsw, (int)dsh); //screen bounds
		if(sBounds.contains((int)l.x, (int)l.y))
		{
			double percentxOver = (l.x-xover)/dsw;
			double percentyOver = (l.y-yover)/dsh;
			return new Location(percentxOver*screenWidth, percentyOver*screenHeight);
		}
		return new Location(-1, -1);
	}
	public Location getVisibleLocationRegardlessOnscreen(Location l)
	{
		//returns the location of where the Location in virtual spcae should be on the screen, (-1, -1) if offscreen
		//returns regardless of onscreen position, could be offscreen, used to draw map bounds and such
		//absolute values must be taken because l.x-xover could return a negate value giving the incorrect location
		double percentxOver = Math.abs((l.x-xover)/dsw);
		double percentyOver = Math.abs((l.y-yover)/dsh);
		return new Location(percentxOver*screenWidth, percentyOver*screenHeight);
	}
}
