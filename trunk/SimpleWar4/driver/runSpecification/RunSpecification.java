package driver.runSpecification;

import editor.MapEditorV2;
import driver.*;

//details how the game should be run

public class RunSpecification
{
	int numberPlayers;
	int mode;
	
	MapEditorV2 mev2;
	
	/*
	 * mode:
	 * 1=map editing
	 * 2=gaming
	 * 3=game setup
	 */
	
	public RunSpecification(int mode)
	{
		this.mode = mode;
	}
	public int getMode()
	{
		return mode;
	}
	public MapEditorV2 getMapEditorV2()
	{
		return mev2;
	}
	public void startGame()
	{
		if(mode == 1)
		{
			mev2 = new MapEditorV2(this);
			mev2.startMainThread();
		}
		else if(mode == 2)
		{
			new MainThreadV2(this);
		}
	}
}
