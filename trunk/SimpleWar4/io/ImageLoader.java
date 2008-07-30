package io;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//loads the image specified by the file name inside if it exists inside the pictures folder

public class ImageLoader
{
	public ImageLoader(){}
	public Image loadImage(String name)
	{
		Image img = null;
		String userdir = System.getProperty("user.dir");
		File f = new File(userdir+"\\gameData\\pictures\\"+name);
		try {
		    img = ImageIO.read(f);
		    System.out.println("loaded intro pic");
		} catch (IOException e) {
			System.out.println("io exception in loading intro pic");
		}
		return img;
	}
}
