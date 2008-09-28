package controller;

import java.awt.image.BufferedImage;
import rpgWorld.RPGClientWorld;
import graphics.Camera;

public interface RPGController
{
	public BufferedImage getWorldPic();
	public void setWindowed(boolean setter);
	public Camera getCamera();
	public RPGClientWorld getRPGClientWorld();
}