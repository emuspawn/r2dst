package editor;

import driver.MainThread;
import world.World;

public class MapEditorV1
{
	MainThread mt;
	World w;
	
	public MapEditorV1()
	{
		mt = new MainThread(true);
		w = mt.getWorld();
	}
	public static void main(String [] args)
	{
		new MapEditorV1();
	}
}
