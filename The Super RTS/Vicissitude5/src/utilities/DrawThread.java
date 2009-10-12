package utilities;

import graphics.Camera;
import ui.GameDrawer;

/**
 * simple utility that automatically iterates a thread drawing the game
 * @author Jack
 *
 */
public class DrawThread implements Runnable
{
	private GameDrawer gd;
	ImageRepository ir;
	int threadSpeed;
	Camera c;
	boolean end = false;
	
	public DrawThread(GameDrawer gd, Camera c, ImageRepository ir, int threadSpeed)
	{
		this.gd = gd;
		this.ir = ir;
		this.c = c;
		this.threadSpeed = threadSpeed;
		new Thread(this).start();
	}
	public void run()
	{
		while(!end)
		{
			gd.drawGame(c, ir);
			try
			{
				Thread.sleep(threadSpeed);
			}
			catch(InterruptedException e){}
		}
	}
	public void terminateThread()
	{
		end = true;
	}
}
