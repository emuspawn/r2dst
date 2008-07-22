package world;

import world.unit.*;
import ui.menu.*;
import world.terrain.*;
import editor.Editor;

public class World
{
	UnitEngine ue;
	MenuCheckEngine mce;
	Editor editor;
	
	int mapWidth = 605;
	int mapHeight = 600;
	Terrain[] terrain = new Terrain[10];
	boolean editMode;
	
	public World(boolean editMode, Editor editor)
	{
		this.editMode = editMode;
		this.editor = editor;
		ue = new UnitEngine(this);
		mce = new MenuCheckEngine(this);
	}
	public void setTerrain(Terrain[] t)
	{
		terrain = t;
	}
	public Editor getEditor()
	{
		return editor;
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
