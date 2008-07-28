package driver.runSpecification;

import editor.MapEditorV2;
import driver.*;

//details how the game should be run

public class RunSpecification
{
	int numberPlayers;
	int mode;
	
	//1
	MapEditorV2 mev2;
	
	//2
	boolean gStart = false; //true at the start of a game
	
	//3
	NormalSkirmishSpecification nss;
	
	/*
	 * mode:
	 * 1=map editing
	 * 2=normal skirmish game
	 * 3=game setup
	 */
	
	public RunSpecification(int mode)
	{
		this.mode = mode;
		
		nss = new NormalSkirmishSpecification();
	}
	public boolean getGameStart()
	{
		return gStart;
	}
	public void setGameStart(boolean setter)
	{
		gStart = setter;
	}
	public NormalSkirmishSpecification getNormalSkirmishSpecification()
	{
		return nss;
	}
	public void setMode(int setter)
	{
		mode = setter;
	}
	public int getMode()
	{
		return mode;
	}
	public MapEditorV2 getMapEditorV2()
	{
		return mev2;
	}
}
