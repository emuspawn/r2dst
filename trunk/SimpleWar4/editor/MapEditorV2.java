package editor;

import driver.*;
import driver.runSpecification.*;
import java.awt.Point;
import world.World;
import world.terrain.*;

public class MapEditorV2
{
	MainThreadV2 mt;
	World w;
	Terrain[] t;
	
	/*
	 * (protected int editType) edit type:
	 * 1=draw water
	 */
	
	public MapEditorV2(RunSpecification rs)
	{
		clearAllEditData();
		
		System.out.println("main thread v2 created");
		mt = new MainThreadV2(rs);
		
		w = mt.getWorld();
	}
	public void startMainThread()
	{
		mt.startMainThread();
	}
	public void clearAllEditData()
	{
		t = new Terrain[10];
	}
	public void performEditorFunctions()
	{
		if(w != null)
		{
			w.setTerrain(t);
		}
	}
	public void interpretMouseClick(Point p)
	{
		
	}
}
