package driver;

import graphics.*;
import driver.runSpecification.*;
import world.World;
import editor.*;

public class MainThreadV2
{
	GraphicsFinder gf;
	GameDrawer gd;
	World w;
	Camera c;
	
	RunSpecification rs;
	
	public MainThreadV2(RunSpecification rs)
	{
		System.out.println("program starting");
		this.rs = rs;
		System.out.println("run specification chosen");
		c = new Camera();
		System.out.println("camera instantiated");
		w = new World(rs, c);
		System.out.println("world instantiated");
		gf = new GraphicsFinder(w, c);
		c.setActualScreenDimensions(gf.getWidth(), gf.getHeight());
		System.out.println("graphics finder instantiated");
		gd = new GameDrawer(gf, w, c);
		System.out.println("game drawer instantiated");
		System.out.println();
		
		if(rs.getMode() != 1)
		{
			performGameFunctions();
		}
	}
	public static void main(String [] args)
	{
		new MainThreadV2(new RunSpecification(3));
	}
	public World getWorld()
	{
		//called by the editor to get a copy of important game variables
		return w;
	}
	public void startMainThread()
	{
		performGameFunctions();
	}
	private void performGameFunctions()
	{
		for(;;)
		{
			gd.performGameDrawFunctions();
			w.performWorldFunctions();
			
			if(rs.getMode() == 1)
			{
				rs.getMapEditorV2().performEditorFunctions();
			}
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException e){}
			//System.out.println("main thread iteration completed");
		}
	}
}
