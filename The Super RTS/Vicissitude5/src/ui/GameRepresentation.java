package ui;

import graphics.Camera;
import java.awt.Graphics2D;
import utilities.ImageRepository;

/**
 * used by the drawer to draw the game
 * @author Jack
 *
 */
public abstract class GameRepresentation
{
	/**
	 * draws the game
	 * @param g the graphic object refering to the surface
	 * that is going to be drawn to
	 * @param ir the image repository holding the game sprites
	 */
	public abstract void drawGame(Graphics2D g, Camera c, ImageRepository ir);
}
