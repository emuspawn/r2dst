package rpgWorld.unit.unitType;

import rpgWorld.unit.Unit;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import graphics.Camera;
import java.awt.image.BufferedImage;
import utilities.ImageUtil;

public class Hero extends Unit
{
	public Hero()
	{
		super(new Point(500, 500));
	}
	public void drawUnit(Graphics g, Camera c)
	{
		if(c.getOnScreen(getBounds()))
		{
			int so2 = size/2;
			//the image length is such that when rotated, no pixel is outside the image
			int imgLength = (int)(Math.sqrt((size*size)/2)*2);
			BufferedImage bi = new BufferedImage(imgLength, imgLength, BufferedImage.TYPE_INT_RGB);
			Graphics big = bi.getGraphics();
			big.setColor(Color.green);
			big.fillRect(0, 0, imgLength, imgLength);
			big.setColor(Color.red);
			//draws it in the center of the image
			big.fillRect((imgLength/2)-so2, (imgLength/2)-so2, size, size);
			
			int x = (int)location.x - so2;
			int y = (int)location.y - so2;
			Point p = c.getScreenLocation(new Point(x, y));
			g.drawImage(ImageUtil.rotateImage(bi, size, angle, Color.green), p.x, p.y, null);
		}
	}
}
