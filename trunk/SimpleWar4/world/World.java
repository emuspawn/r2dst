package world;

import world.unit.*;
import world.controller.*;
import driver.runSpecification.*;
import world.map.*;
import ui.menu.*;
import world.terrain.*;
import editor.Editor;
import java.awt.Color;
import world.unit.race.*;
import graphics.Camera;
import world.pathFinder.*;

public class World
{
	UnitEngine ue;
	MenuCheckEngine mce;
	Editor editor;
	RunSpecification rs;
	PathFindingEngine pfe;
	
	Map m;
	int mapWidth = 900;
	int mapHeight = 900;
	Terrain[] terrain = new Terrain[10];
	boolean editMode;
	Controller[] c; //the players in game (human or AI)
	
	boolean gameStart = false;
	
	public World(RunSpecification rs, Camera camera)
	{
		this.rs = rs;
		mce = new MenuCheckEngine(this);
		ue = new UnitEngine(this, camera);
		
		pfe = new PathFindingEngine(ue);
		
		setupGame();
	}
	public World(boolean editMode, Editor editor)
	{
		this.editMode = editMode;
		this.editor = editor;
		ue = new UnitEngine(this, null);
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
				
				//sets up the controllers for the players
				c = new Controller[rs.getNormalSkirmishSpecification().getPlayerAmount()];
				for(int i = 0; i < c.length; i++)
				{
					if(i == 0)
					{
						c[i] = new Controller((i+1), Color.red, new TestTeam1Race());
						System.out.println("player 1 created");
					}
					else if(i == 1)
					{
						c[i] = new Controller((i+1), Color.blue, new TestTeam1Race());
						System.out.println("player 2 created");
					}
				}
				
				ue.spawnStartUnits();
			}
		}
		else
		{
			ue.performUnitFunctions();
		}
		mce.performMenuCheckFunctions();
	}
	public Controller[] getControllers()
	{
		return c;
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
