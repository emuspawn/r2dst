package driver;

import graphics.*;
import world.World;
import editor.*;

public class MainThread
{
	GraphicsFinder gf;
	GameDrawer gd;
	World w;
	Camera c;
	Editor e;
	
	boolean editMode; //whether or not the game is in the editing mode
	
	public MainThread(boolean editMode, Editor e)
	{
		this.editMode = editMode;
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
		
		if(editMode)
		{
			this.e = e;
		}
		
		performGameFunctions();
	}
	public static void main(String [] args)
	{
		new MainThread(false, null);
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
			
			if(editMode)
			{
				e.performEditorFunctions();
			}
			
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException e){}
		}
	}
}
