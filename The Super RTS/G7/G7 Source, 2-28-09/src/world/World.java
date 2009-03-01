package world;

import world.shot.Shot;
import superIO.CustomClassLoader;
import world.resource.*;
import ai.*;
import world.terrain.*;
import world.dynamicMap.*;
import world.unit.UnitEngine;
import utilities.Location;
import java.util.ArrayList;
import world.unit.Unit;
import world.shot.ShotEngine;
import java.io.*;
import java.awt.Color;
import java.awt.Graphics2D;
import pathFinder.PathFinder;
import pathFinder.pathFinders.DirectMovePF;
import io.*;
import world.owner.*;
import graphics.Camera;
import display.worldDisplay.*;

/**
 * Instantiating an instance of this class runs the game. <br /><br />
 * 
 * Regulates all world characteristics.
 */

public class World implements Runnable
{
	ArrayList<Owner> o = new ArrayList<Owner>();
	Location[] startLocations;
	ArrayList<AIThread> ait = new ArrayList<AIThread>();

	DynamicMap dm;
	UnitEngine ue;
	ShotEngine se;
	BuildEngine be;
	ResourceEngine re;
	
	WorldOverlay wo;
	PathFinder pf;
	
	int threadSpeed = 20;
	
	/**
	 * constructs a World for editing usages
	 * @param width
	 * @param height
	 */
	public World(int width, int height)
	{
		//for creating the map editor
		dm = new DynamicMap(width, height);
		ue = new UnitEngine(dm);
		se = new ShotEngine(ue);
		be = new BuildEngine(this);
		re = new ResourceEngine(this);
	}
	public Location[] getStartLocations()
	{
		return startLocations;
	}
	/**
	 * constructs a World that actually plays the game
	 * @param mapFileName
	 * @param AIFileNames
	 */
	public World(String mapFileName, ArrayList<String> AIFileNames)
	{
		//for playing the game
		dm = new DynamicMap(0, 0);
		
		ue = new UnitEngine(dm);
		se = new ShotEngine(ue);
		be = new BuildEngine(this);
		re = new ResourceEngine(this);
		
		try
		{
			File f = new File(mapFileName);
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			MapReader.readMap(this, dis);
		}
		catch(IOException e){}
		
		wo = new WorldOverlay(this);
		pf = new DirectMovePF(getWidth(), getHeight());
		
		//setUpGame(AIFileNames);
		ArrayList<AI> ais = setUpGame(AIFileNames);
		for(int i = 0; i < ais.size(); i++)
		{
			ait.add(new AIThread(ais.get(i), this));
		}
		
		new DrawFrame(this);
		
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
		String map = new String(System.getProperty("user.dir")+System.getProperty("file.separator")+"resourceSquare.wrld");
		ArrayList<String> ais = new ArrayList<String>();
		ais.add("TestAI.class"); //bottom left
		ais.add("TestAI2.class"); //top right
		
		System.out.println("map = "+map);
		System.out.println("AIs = ");
		for(int i = 0; i < ais.size(); i++)
		{
			System.out.println(ais.get(i));
		}
		new World(map, ais);
	}
	private ArrayList<AI> setUpGame(ArrayList<String> AIFileNames)
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
		return ais;
	}
	private AI loadAI(String AIFileName, Owner o, CustomClassLoader ccl)
	{
		try
		{
			System.out.println("loading "+AIFileName);
			Class c = ccl.loadClass(AIFileName);
			Class[] args = new Class[3];
			args[0] = o.getClass();
			args[1] = wo.getClass();
			args[2] = pf.getClass().getSuperclass();
			return (AI)CustomClassLoader.constructObject(c, args, o, wo, pf);
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
		//System.out.println("unit registered, "+u.getName()+", "+u.getLocation()+", owner = "+u.getOwner().getName());
	}
	public void registerElement(Resource r)
	{
		dm.addElement(r);
		ue.registerUnit(r);
		re.registerResource(r);
	}
	public void registerElement(Terrain t)
	{
		dm.addElement(t);
	}
	/**
	 * gets a reference to the BuildEngine
	 * @return returns a reference to the BuildEngine
	 */
	public BuildEngine getBuildEngine()
	{
		return be;
	}
	/**
	 * gets a reference to the UnitEngine
	 * @return returns a reference to the UnitEngine
	 */
	public UnitEngine getUnitEngine()
	{
		return ue;
	}
	public void run()
	{
		for(int i = 0; i < ait.size(); i++)
		{
			ait.get(i).start();
		}
		for(;;)
		{
			ue.performUnitFunctions();
			se.performShotFunctions();
			be.performBuildEngineFunctions();
			re.performResourceFunctions();
			try
			{
				Thread.sleep(threadSpeed);
			}
			catch(InterruptedException e){}
		}
	}
	/**
	 * sets the thread speed for the main game thread
	 * @param setter the new thread speed
	 */
	public void setThreadSpeed(int setter)
	{
		threadSpeed = setter;
	}
	/**
	 * draws the world's visible elements and shots
	 * @param g the draw graphics
	 * @param c the camera
	 */
	public void drawWorld(Graphics2D g, Camera c)
	{
		g.setColor(Color.green);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
		ArrayList<Element> e = dm.getVisibleElements(c);
		for(int i = e.size()-1; i >= 0; i--)
		{
			e.get(i).drawElement(g, c);
		}
		
		ArrayList<Shot> s = se.getShots();
		for(int i = 0; i < s.size(); i++)
		{
			s.get(i).drawShot(g, c);
		}
		
		//dm.drawPartitionGrid(g, c);
	}
	/**
	 * gets the resource engine
	 * @return returns an alias of the resource engine for the world
	 */
	public ResourceEngine getResourceEngine()
	{
		return re;
	}
}
