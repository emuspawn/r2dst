package driver;

import graphics.*;
import world.World;

public class MainThread
{
	GraphicsFinder gf;
	GameDrawer gd;
	World w;
	Camera c;
	
	public MainThread(boolean editMode)
	{
		System.out.println("program starting");
		w = new World(editMode);
		System.out.println("world instantiated");
		c = new Camera();
		System.out.println("camera instantiated");
		gf = new GraphicsFinder(w, c);
		c.setActualScreenDimensions(gf.getWidth(), gf.getHeight());
		System.out.println("graphics finder instantiated");
		gd = new GameDrawer(gf, w, c);
		System.out.println("game drawer instantiated");
		System.out.println();
		
		performGameFunctions();
	}
	public static void main(String [] args)
	{
		new MainThread(false);
	}
	public World getWorld()
	{
		//called by the editor to get a copy of important game variables
		return w;
	}
	private void performGameFunctions()
	{
		for(;;)
		{
			gd.performGameDrawFunctions();
			w.performWorldFunctions();
			
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException e){}
		}
	}
}
