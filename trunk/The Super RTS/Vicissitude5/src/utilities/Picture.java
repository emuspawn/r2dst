package utilities;

import graphics.Camera;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * stores an image and the location of that image
 * @author Jack
 *
 */
public class Picture
{
	private String name;
	private BufferedImage bi;
	private Location location;
	
	public Picture(String name, BufferedImage bi, Location location)
	{
		this.name = name;
		this.bi = bi;
		this.location = location;
	}
	public String getName()
	{
		return name;
	}
	public BufferedImage getBufferedImage()
	{
		return bi;
	}
	/**
	 * gets the location of the center of the image
	 * @return
	 */
	public Location getLocation()
	{
		return location;
	}
	public void setLocation(Location l)
	{
		location = l;
	}
	public void drawPicture(Graphics2D g, Camera c)
	{
		Point p = location.getPoint();
		if(c != null)
		{
			p =  c.getScreenLocation(location);
		}
		g.drawImage(bi, p.x-bi.getWidth()/2, p.y-bi.getHeight()/2, null);
	}
}
