package utilities;

import java.io.*;
import java.awt.Color;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * loads and stores images for future retrieval, images must
 * be in the form "name width height .fileType"
 * @author Secondary
 *
 */
public class ImageRepository
{
	HashMap<String, BufferedImage> m = new HashMap<String, BufferedImage>();
	
	/**
	 * loads the images in the given directory
	 * @param f the directory containing the images
	 * to be loaded
	 */
	public ImageRepository(File f)
	{
		System.out.println("loading images...");
		loadImages(f);
		System.out.println("done");
	}
	/**
	 * recursively loads the all the images in
	 * a given directory and all subdirectories
	 * @param f the starting directory
	 */
	private void loadImages(File f)
	{
		if(f.isDirectory())
		{
			System.out.println("looking in "+f.getAbsolutePath()+"...");
			for(File temp: f.listFiles())
			{
				loadImages(temp);
			}
		}
		else
		{
			try
			{
				System.out.print("loading "+f.getName()+"... ");
				String name = f.getName();
				String[] s = name.split(" ");
				name = s[0];
				double width = Integer.parseInt(s[1]);
				double height = Integer.parseInt(s[2]);
				try
				{
					BufferedImage bi = ImageIO.read(f);
					System.out.print("scaling... ");
					AffineTransform at = new AffineTransform();
				    at.scale(width/bi.getWidth(), height/bi.getHeight());
				    AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
					bi = op.filter(bi, null);
					
					bi = filterColor(bi, new Color(255, 255, 255), 30);
					
					m.put(name, bi);
				}
				catch(IOException e){}
				System.out.println("done");
			}
			catch(Exception e)
			{
				System.out.println("failed to load");
			}
		}
	}
	private BufferedImage filterColor(BufferedImage bi, Color filter, int range)
	{
		System.out.print("filtering... ");
		for(int x = bi.getWidth()-1; x >= 0; x--)
		{
			for(int y = bi.getHeight()-1; y >= 0; y--)
			{
				int c = bi.getRGB(x,y);
				int  red = (c & 0x00ff0000) >> 16;
				int  green = (c & 0x0000ff00) >> 8;
				int  blue = c & 0x000000ff;
				if(red > filter.getRed()-range && red < filter.getRed()+range
						&& green > filter.getGreen()-range && green < filter.getGreen()+range
						&& blue > filter.getBlue()-range && blue < filter.getBlue()+range)
				{
					bi.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
				}
			}
		}
		return bi;
	}
	public BufferedImage getImage(String key)
	{
		return m.get(key);
	}
}
