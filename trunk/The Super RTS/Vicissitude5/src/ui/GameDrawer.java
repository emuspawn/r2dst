package ui;

import graphics.Camera;

import java.awt.Canvas;
import java.awt.Graphics2D;
import ui.GameFrame;
import java.awt.image.*;
import utilities.ImageRepository;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.AffineTransform;

/**
 * the surface onto which the game is drawn
 * @author Jack
 *
 */
public class GameDrawer extends Canvas implements FocusListener
{
	GameFrame gf;
	GameRepresentation gr;
	BufferStrategy bs;
	
	public GameDrawer(GameFrame gf, GameRepresentation gr)
	{
		this.gf = gf;
		this.gr = gr;
		setSize(gf.getWidth(), gf.getHeight());
		//gf.setSize(gf.getWidth(), gf.getHeight());
	}
	private void createBufferStrategy()
	{
		createBufferStrategy(3);
		bs = getBufferStrategy();
	}
	private void setupTransform(Graphics2D g, Camera c)
	{
		AffineTransform at = new AffineTransform();
		//at.scale((1.0*getWidth()/c.getWidth())*c.getScale(), (1.0*getHeight()/c.getHeight())*c.getScale());
		at.scale(c.getScale(), c.getScale());
		at.rotate(Math.toRadians(c.getRotatation()), c.getWidth()/2, c.getHeight()/2);
		g.setTransform(at);
		c.setWidth((int)(getWidth()/c.getScale()));
		c.setHeight((int)(getHeight()/c.getScale()));
	}
	/**
	 * draws the game as determined by the passed game representation
	 * object in the constructor
	 * @param ir
	 */
	public void drawGame(Camera c, ImageRepository ir)
	{
		if(bs != null)
		{
			try
			{
				Graphics2D g = (Graphics2D)bs.getDrawGraphics();
				setupTransform(g, c);
				
				gr.drawGame(g, c, ir);
				
				bs.show();
				g.dispose();
			}
			catch(NullPointerException e){} //has to do with resizing the canvas
		}
		else
		{
			createBufferStrategy();
			drawGame(c, ir);
		}
	}
	public void focusGained(FocusEvent e)
	{
		System.out.println("focus gained (in ui.Drawer)");
		gf.requestFocus();
	}
	public void focusLost(FocusEvent e){}
}
