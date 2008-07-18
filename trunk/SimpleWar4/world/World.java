package world;

import world.unit.*;
import ui.menu.*;
import world.terrain.*;

public class World
{
	UnitEngine ue;
	MenuCheckEngine mce;
	
	int mapWidth = 2000;
	int mapHeight = 2000;
	Terrain[] terrain = new Terrain[10];
	boolean editMode;
	
	public World(boolean editMode)
	{
		this.editMode = editMode;
		ue = new UnitEngine(this);
		mce = new MenuCheckEngine(this);
	}
	public boolean getEditMode()
	{
		return editMode;
	}
	public UnitEngine getUnitEngine()
	{
		return ue;
	}
	public int getMapHeight()
	{
		return mapHeight;
	}
	public int getMapWidth()
	{
		return mapWidth;
	}
	public void performWorldFunctions()
	{
		ue.performUnitFunctions();
		mce.performMenuCheckFunctions();
	}
	public Terrain[] getTerrain()
	{
		return terrain;
	}
	public MenuCheckEngine getMenuCheckEngine()
	{
		return mce;
	}
}
