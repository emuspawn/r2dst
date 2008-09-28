package utilities;

import javax.imageio.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageUtil
{
	public static BufferedImage loadImage(String dir)
	{
		String userDir = System.getProperty("user.dir");
		File f = new File(userDir+dir);
		try
		{
			return ImageIO.read(f);
		}
		catch(IOException e)
		{
			System.out.println("io exception");
		}
		return null;
	}
	public static BufferedImage rotateImage(BufferedImage in, int size, double angle, Color background)
	{
		//returns a rotated ((double)angle degrees) version of (BufferedImage)in
		int imgLength = (int)(Math.sqrt((size*size)/2)*2);
		BufferedImage out = new BufferedImage(imgLength, imgLength, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D)out.getGraphics();
		g2d.setColor(background);
		g2d.fillRect(0, 0, imgLength, imgLength);
		g2d.drawImage(in, AffineTransform.getRotateInstance(Math.toRadians(angle), imgLength/2, imgLength/2), null);
		return out;
	}
	public static BufferedImage rotateImage(BufferedImage in, int size, double angle, BufferedImage background)
	{
		//returns a rotated ((double)angle degrees) version of (BufferedImage)in
		int imgLength = (int)(Math.sqrt((size*size)/2)*2);
		BufferedImage out = new BufferedImage(imgLength, imgLength, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D)out.getGraphics();
		g2d.drawImage(background, 0, 0, null);
		g2d.drawImage(in, AffineTransform.getRotateInstance(Math.toRadians(angle), imgLength/2, imgLength/2), null);
		return out;
	}
}
