package editor;

import driver.MainThread;
import world.World;

public class MapEditorV1 extends Editor
{
	MainThread mt;
	World w;
	
	int editType = 0;
	
	/*
	 * edit type:
	 * 
	 */
	
	public MapEditorV1()
	{
		mt = new MainThread(true, this);
		w = mt.getWorld();
	}
	public static void main(String [] args)
	{
		new MapEditorV1();
	}
	public void performEditorFunctions()
	{
		
	}
}
