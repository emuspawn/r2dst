package driver;

import graphics.*;
import world.World;

public class MainThread
{
	GraphicsFinder gf;
	GameDrawer gd;
	World w;
	Camera c;
	
	public MainThread()
	{
		System.out.println("program starting");
		w = new World();
		System.out.println("world instantiated");
		c = new Camera();
		System.out.println("camera instantiated");
		gf = new GraphicsFinder(w, c);
		System.out.println("graphics finder instantiated");
		gd = new GameDrawer(gf, w, c);
		System.out.println("game drawer instantiated");
		System.out.println();
		
		performGameFunctions();
	}
	public static void main(String [] args)
	{
		new MainThread();
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
