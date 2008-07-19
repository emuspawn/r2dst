package editor;

import driver.MainThread;
import java.awt.Point;
import world.World;
import world.terrain.*;

public class MapEditorV1 extends Editor
{
	MainThread mt;
	World w;
	Terrain[] t;
	
	/*
	 * (protected int editType) edit type:
	 * 1=draw water
	 */
	
	public MapEditorV1()
	{
		clearAllEditData();
		mt = new MainThread(true, this);
		w = mt.getWorld();
	}
	public void clearAllEditData()
	{
		t = new Terrain[10];
	}
	public static void main(String [] args)
	{
		new MapEditorV1();
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
