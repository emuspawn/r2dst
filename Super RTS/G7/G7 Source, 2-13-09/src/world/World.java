package world;

import superIO.CustomClassLoader;
import ui.GameFrame;
import world.resource.Resource;
import ai.*;
import world.terrain.*;
import world.dynamicMap.*;
import world.unit.UnitEngine;
import driver.WorldConstants;
import utilities.Location;
import java.util.ArrayList;
import world.unit.Unit;
import world.shot.ShotEngine;
import java.io.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import display.WorldCanvas;
import pathFinder.PathFinder;
import pathFinder.pathFinders.DirectMovePF;
import io.*;
import world.owner.*;
import graphics.Camera;
import world.spawn.SpawnEngine;
import display.worldDisplay.*;
import display.*;
import java.awt.Canvas;

/*
 * regulates world characteristics such as resources
 */

public class World implements Runnable
{
	ArrayList<Resource> r = new ArrayList<Resource>();
	ArrayList<Owner> o = new ArrayList<Owner>();
	Location[] startLocations;

	DynamicMap dm;
	UnitEngine ue;
	ShotEngine se;
	BuildEngine be;
	SpawnEngine spe;
	
	WorldOverlay wo;
	BuildEngineOverlay beo;
	PathFinder pf;
	
	WorldCanvas wc;
	
	int threadSpeed = 20;
	
	public World(int width, int height)
	{
		//for creating the map editor
		dm = new DynamicMap(width, height);
		ue = new UnitEngine(dm);
		se = new ShotEngine(ue);
		be = new BuildEngine(ue);
		spe = new SpawnEngine(this);
	}
	public Location[] getStartLocations()
	{
		return startLocations;
	}
	public World(String mapFileName, ArrayList<String> AIFileNames)
	{
		//for playing the game
		dm = new DynamicMap(0, 0);
		
		try
		{
			File f = new File(mapFileName);
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			MapReader.readMap(this, dis);
		}
		catch(IOException e){}
		
		ue = new UnitEngine(dm);
		se = new ShotEngine(ue);
		be = new BuildEngine(ue);
		spe = new SpawnEngine(this);
		
		wo = new WorldOverlay(this);
		beo = new BuildEngineOverlay(this);
		pf = new DirectMovePF(getWidth(), getHeight());
		
		setUpGame(AIFileNames);
		
		new DrawFrame(this);
		
		/*GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		GameFrame gf = new GameFrame(device);
		wc = new WorldCanvas(gf, this, gf.getWidth(), gf.getHeight());
		gf.add(wc);
		new Thread(wc).start();*/
		
		
		new Thread(this).start();
	}
	public void setStartLocations(Location[] l)
	{
		startLocations = l;
	}
	public ShotEngine getShotEngine()
	{
		return se;
	}
	public static void main(String[] args)
	{
		System.out.println("starting world");
		String map = new String(System.getProperty("user.dir")+System.getProperty("file.separator")+"test.wrld");
		ArrayList<String> ais = new ArrayList<String>();
		ais.add("TestAI.class");
		ais.add("TestAI.class");
		
		System.out.println("map = "+map);
		System.out.println("AIs = ");
		for(int i = 0; i < ais.size(); i++)
		{
			System.out.println(ais.get(i));
		}
		new World(map, ais);
	}
	private void setUpGame(ArrayList<String> AIFileNames)
	{
		String dir = System.getProperty("user.dir")+System.getProperty("file.separator")+"customAI"+System.getProperty("file.separator");
		CustomClassLoader ccl = new CustomClassLoader(dir);
		setupOwners(AIFileNames.size());
		ArrayList<AI> ais = new ArrayList<AI>();
		for(int i = 0; i < AIFileNames.size(); i++)
		{
			ais.add(loadAI(AIFileNames.get(i), o.get(i), ccl));
		}
		readStartSetup();
		setInitialPopulationMaximums();
	}
	private AI loadAI(String AIFileName, Owner o, CustomClassLoader ccl)
	{
		try
		{
			System.out.println("loading "+AIFileName);
			Class c = ccl.loadClass(AIFileName);
			Class[] args = new Class[4];
			args[0] = o.getClass();
			args[1] = wo.getClass();
			args[2] = beo.getClass();
			args[3] = pf.getClass().getSuperclass();
			//args[3] = null;
			return (AI)CustomClassLoader.constructObject(c, args, o, wo, beo, pf);
		}
		catch(ClassNotFoundException a)
		{
			a.printStackTrace();
		}
		return null;
	}
	public void setSize(int width, int height)
	{
		dm.setSize(width, height);
	}
	public DynamicMap getDynamicMap()
	{
		return dm;
	}
	public int getWidth()
	{
		return dm.getWidth();
	}
	public int getHeight()
	{
		return dm.getHeight();
	}
	private void readStartSetup()
	{
		//loads the units, buildings, etc. for the start of the game
		try
		{
			File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"default.ss");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			StartSetupReader.readStartSetup(this, dis);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	private void setupOwners(int players)
	{
		System.out.println("setting up owners...");
		for(int i = 0; i < players; i++)
		{
			o.add(new Owner("comp "+(i+1), WorldConstants.teamColors[i], startLocations[i]));
		}
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
	public ArrayList<Owner> getOwners()
	{
		return o;
	}
	public void registerElement(Unit u)
	{
		dm.addElement(u);
		ue.registerUnit(u);
		System.out.println("unit registered, "+u.getName()+", owner = "+u.getOwner().getName());
	}
	public void registerElement(Resource r)
	{
		dm.addElement(r);
		ue.registerUnit(r);
		spe.registerSpawner(r);
	}
	public void registerElement(Terrain t)
	{
		dm.addElement(t);
	}
	public BuildEngine getBuildEngine()
	{
		return be;
	}
	public UnitEngine getUnitEngine()
	{
		return ue;
	}
	public void run()
	{
		for(;;)
		{
			ue.performUnitFunctions();
			se.performShotFunctions();
			be.performBuildEngineFunctions();
			spe.performSpawnEngineFunctions();
			try
			{
				Thread.sleep(threadSpeed);
			}
			catch(InterruptedException e){}
		}
	}
	public void setThreadSpeed(int setter)
	{
		threadSpeed = setter;
	}
	public ArrayList<Resource> getResources()
	{
		return r;
	}
	public void drawWorld(Graphics2D g, Camera c)
	{
		g.setColor(Color.green);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
		ArrayList<Element> e = dm.getVisibleElements(c);
		for(int i = e.size()-1; i >= 0; i--)
		{
			e.get(i).drawElement(g, c);
		}
	}
}
