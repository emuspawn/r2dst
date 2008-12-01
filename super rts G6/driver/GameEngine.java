package driver;

import world.unit.UnitEngine;
import graphics.Camera;
import startup.Starter;
import world.unit.Unit;
import world.unit.units.*;
import world.unit.building.buildings.*;
import world.*;
import display.DrawFrame;
import map.Map;
import java.util.ArrayList;
import owner.Owner;
import java.awt.Color;
import pathFinder.pathFinders.*;
import pathFinder.PathFinder;
import world.resource.Resource;
import shot.ShotEngine;

public class GameEngine implements Runnable
{
	UnitEngine ue;
	World w;
	DrawFrame df;
	Map m;
	ArrayList<Owner> o;
	GameEngineOverlay geo;
	BuildEngine be;
	ShotEngine se;
	BuildEngineOverlay beo;
	PathFinder pf;
	Starter starter;
	int threadSpeed = 20;
	Camera c = new Camera();;
	
	public boolean moveUp = false;
	public boolean moveRight = false;
	public boolean moveDown = false;
	public boolean moveLeft = false;
	
	public GameEngine()
	{
		m = new Map();
		ue = new UnitEngine();
		w = new World(m);
		se = new ShotEngine(ue);
		df = new DrawFrame(this, w, ue, se, m, c);
		geo = new GameEngineOverlay(w, ue, m);
		be = new BuildEngine(ue);
		beo = new BuildEngineOverlay(be);
		pf = new DirectMovePF(m.getMapWidth(), m.getMapHeight());
		
		setupOwners();

		Thread t = new Thread(this);
		starter = new Starter(t, o.get(0), o.get(1), geo, beo, pf, m);
		//setupAIThreads();
		
		
		//t.start();
	}
	public void setThreadSpeed(int setter)
	{
		threadSpeed = setter;
	}
	public void run()
	{
		createStartingBuildings();
		createStartingUnits();
		setInitialPopulationMaximums();
		
		String winner = new String(); //the winner of the game
		String using = new String(); //the ai the winner was using
		starter.dispose();
		Thread t = new Thread(df.getDrawCanvas());
		t.start();
		boolean continuing = false;
		boolean gameOver = false;
		while(!gameOver)
		{
			try
			{
				w.performWorldFunctions();
				se.performShotFunctions();
				ue.performUnitFunctions();
				be.performBuildEngineFunctions();
				updateCameraPosition();
				
				for(int i = o.size()-1; i >= 0; i--)
				{
					if(o.get(i).getUnitCount() == 0 && o.get(i).getCurrentUnitMax() == 0)
					{
						gameOver = true;
						for(int q = o.size()-1; q >= 0; q--)
						{
							if(q != i)
							{
								winner+=o.get(q).getName()+" ";
								using+=o.get(q).getAIUsing()+" ";
								break;
							}
						}
						break;
					}
				}
				
				try
				{
					Thread.sleep(threadSpeed);
				}
				catch(InterruptedException e){}
			}
			catch(Exception e)
			{
				System.out.println("exception caught in game engine");
				e.printStackTrace();
				continuing = true;
			}
			if(continuing)
			{
				System.out.println("exception caught in game engine, continuing normally");
			}
		}
		df.displayWinScreen(winner, using);
	}
	private void updateCameraPosition()
	{
		if(moveUp)
		{
			c.translate(0, -10);
		}
		if(moveRight)
		{
			c.translate(10, 0);
		}
		if(moveDown)
		{
			c.translate(0, 10);
		}
		if(moveLeft)
		{
			c.translate(-10, 0);
		}
	}
	public ArrayList<Owner> getOwners()
	{
		return o;
	}
	private void setInitialPopulationMaximums()
	{
		int popCap;
		int currentPop;
		for(int i = 0; i < o.size(); i++)
		{
			popCap = 0;
			currentPop = 0;
			ArrayList<Unit> u = ue.getFriendlyUnits(o.get(i));
			for(int a = 0; a < u.size(); a++)
			{
				currentPop += u.get(a).getPopulationValue();
				popCap += u.get(a).getPopulationAugment();
			}
			o.get(i).setCurrentUnitMax(be, popCap);
			o.get(i).setUnitCount(be, currentPop);
		}
	}
	private void createStartingBuildings()
	{
		for(int i = 0; i < o.size(); i++)
		{
			ue.getUnits().add(new HQ(o.get(i), m.getStartingLocations()[i]));
		}
	}
	private void createStartingUnits()
	{
		for(int i = 0; i < o.size(); i++)
		{
			ue.getUnits().add(new Worker(o.get(i), m.getStartingLocations()[i]));
		}
		//ue.getUnits().add(new Worker1(o.get(1), new Location(120, 120)));
	}
	private void setupOwners()
	{
		Resource[] r = w.getResourceList();
		o = new ArrayList<Owner>();
		o.add(new Owner("comp 1", Color.red, r));
		o.add(new Owner("comp 2", Color.blue, r));
	}
	public static void main(String[] args)
	{
		new GameEngine();
	}
}
