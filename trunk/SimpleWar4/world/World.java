package world;

import world.unit.*;
import driver.runSpecification.*;
import world.map.*;
import ui.menu.*;
import world.terrain.*;
import editor.Editor;

public class World
{
	UnitEngine ue;
	MenuCheckEngine mce;
	Editor editor;
	RunSpecification rs;
	
	Map m;
	int mapWidth = 900;
	int mapHeight = 900;
	Terrain[] terrain = new Terrain[10];
	boolean editMode;
	
	boolean gameStart = false;
	
	public World(RunSpecification rs)
	{
		this.rs = rs;
		mce = new MenuCheckEngine(this);
		ue = new UnitEngine(this);
		setupGame();
	}
	public World(boolean editMode, Editor editor)
	{
		this.editMode = editMode;
		this.editor = editor;
		ue = new UnitEngine(this);
		mce = new MenuCheckEngine(this);
		
		if(!editMode)
		{
			setupGame();
		}
	}
	private void setupGame()
	{
		m = new TestMap();
		mapWidth = m.getMapWidth();
		mapHeight = m.getMapHeight();
		terrain = m.getTerrain();
	}
	public void setTerrain(Terrain[] t)
	{
		terrain = t;
	}
	public RunSpecification getRunSpecification()
	{
		return rs;
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
	public Map getMap()
	{
		return m;
	}
	public void performWorldFunctions()
	{
		if(!gameStart)
		{
			if(rs.getGameStart())
			{
				gameStart = true;
			}
		}
		else
		{
			ue.performUnitFunctions();
		}
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
