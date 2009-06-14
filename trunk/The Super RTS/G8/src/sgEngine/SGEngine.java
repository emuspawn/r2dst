package sgEngine;

import graphics.GLCamera;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import display.*;
//import dynamicMap3D.MapDisplay;
import sgEngine.userAction.*;
import ui.GLFrame;
import utilities.Location;
import world.World;
import world.owner.Owner;
import world.unit.Unit;
import ai.AI;
import ai.computerAI.computerAIs.*;
import ai.humanAI.basicHumanAI.TestHumanAI;

/**
 * the engine that accepts input and runs the game
 * @author Jack
 *
 */
public class SGEngine implements Runnable
{
	int icount = 0; //iteration count
	World w;
	ArrayList<Owner> o = new ArrayList<Owner>();
	HashMap<Integer, ArrayList<UserAction>> userActions = new HashMap<Integer, ArrayList<UserAction>>();
	HashMap<Integer, ArrayList<BuildOrder>> buildOrders = new HashMap<Integer, ArrayList<BuildOrder>>();
	
	/**
	 * the owner of this SGEngine, all user actions are attributed
	 * to this owner
	 */
	Owner owner;
	UserActionListener ual;
	
	GLCamera c;
	KeyActionListener ka;
	//MapDisplay unitMapDisplay;
	
	public SGEngine()
	{
		incrementEngineRunCount();
		w = new World(700, 100, 700);
		
		//c = new GLCamera(new Location(0, 10, 0), new Location(0, 0, -5), 200, 200);
		c = new GLCamera(new Location(0, 150, 180), new Location(0, 140, 175), 200, 200);
		
		GLFrame f = new GLFrame(new SGDisplay(this, w, c));
		ka = new KeyActionListener();
		f.getGLCanvas().addKeyListener(ka);
		f.getGLCanvas().requestFocus();
		
		ual = new UserActionListener(this, owner, c, false);
		f.getGLCanvas().addMouseListener(ual);
		f.getGLCanvas().addKeyListener(ual);
		f.getGLCanvas().addMouseMotionListener(ual);
		
		/*if(EngineConstants.startUnitMapDisplayWindow)
		{
			GLCamera mapCamera = new GLCamera(new Location(0, 10, 0), new Location(0, 0, -15), 0, 0);
			unitMapDisplay = new MapDisplay(mapCamera);
		}*/
		
		new Thread(this).start();
	}
	/**
	 * loads the engine run count (or creates one if it cant find it) and
	 * increments it by 1, the run count is simply a measure of how
	 * many times the engine has been run
	 */
	private void incrementEngineRunCount()
	{
		int total = 0;
		File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"runcount.d");
		try
		{
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			total = dis.readInt();
		}
		catch(IOException e){}
		total++;
		try
		{
			FileOutputStream fos = new FileOutputStream(f);
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeInt(total);
		}
		catch(IOException e){}
		EngineConstants.engineRunCount = total;
		System.out.println("engine run count = "+total);
	}
	public ArrayList<Owner> getOwners()
	{
		return o;
	}
	/**
	 * sets the owner of this SGEngine, all generated user actions
	 * are attributed to this owner
	 * @param o
	 */
	public void setOwner(Owner o)
	{
		owner = o;
		ual.setOwner(o);
	}
	/**
	 * cancels a queued build order
	 * @param name the name of the unit to be built
	 * @param runTime when the unit is to be built
	 */
	public void cancelBuildOrder(String name, int runTime)
	{
		try
		{
			buildOrders.get(runTime).remove(name);
		}
		catch(NullPointerException e){}
	}
	public void run()
	{
		o.add(new Owner("test owner 3", Color.red));
		o.get(0).setAI(new TestHumanAI(o.get(0), w, this, c));
		setOwner(o.get(0));
		
		/*o.add(new Owner("test owner 1", Color.red));
		o.get(0).setAI(new CrusherAI(o.get(0), w, this));
		setOwner(o.get(0));*/
		
		o.add(new Owner("test owner 2", Color.blue));
		o.get(1).setAI(new CrusherAI(o.get(1), w, this));
		
		o.add(new Owner("test owner 7", Color.cyan));
		o.get(2).setAI(new CrusherAI(o.get(2), w, this));
		
		o.add(new Owner("test owner 8", Color.magenta));
		o.get(3).setAI(new CrusherAI(o.get(3), w, this));
		
		o.add(new Owner("sd8", Color.orange));
		o.get(4).setAI(new CrusherAI(o.get(4), w, this));
		
		o.add(new Owner("ssff", Color.black));
		o.get(5).setAI(new CrusherAI(o.get(5), w, this));
		
		o.add(new Owner("12153", Color.gray));
		o.get(6).setAI(new CrusherAI(o.get(6), w, this));
		
		
		Iterator<Owner> q = o.iterator();
		while(q.hasNext())
		{
			Owner owner = q.next();
			for(int x = 0; x < 1; x++)
			{
				Location l = new Location(Math.random()*w.getWidth()-w.getWidth()/2, 0,
						Math.random()*w.getDepth()-w.getDepth()/2);
				Unit u = EngineConstants.unitFactory.makeUnit("worker", owner, l);
				u.setLocation(new Location(l.x, l.y+u.getRestingHeight(), l.z));
				w.registerElement(u);
			}
			//w.registerElement(WorldConstants.unitFactory.makeUnit("test unit 1", owner, new Location(200, 0, 200)));
			//w.registerElement(WorldConstants.unitFactory.makeUnit("test unit 1", owner, new Location(200, 0, 200)));
			//w.registerElement(WorldConstants.unitFactory.makeUnit("test unit 1", owner, new Location(200, 0, 200)));
		}
		
		for(;;)
		{
			try
			{
				ka.updateCamera(c);
				performedQueuedUserActions(icount);
				performQueuedBuildOrders(icount);
				w.performWorldFunctions();
				Iterator<Owner> i = o.iterator();
				while(i.hasNext())
				{
					AI ai = i.next().getAI();
					if(ai != null)
					{
						ai.performAIFunctions();
					}
				}
				try
				{
					Thread.sleep(20);
				}
				catch(InterruptedException e){}
				icount++;
				
				/*if(EngineConstants.startUnitMapDisplayWindow)
				{
					unitMapDisplay.setMap(w.getUnitEngine().getUnitMap());
					unitMapDisplay.updateMapDisplay();
				}*/
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("attempting to continue normally");
			}
		}
	}
	/**
	 * gets the current interation count for the main thread of the SGEngine,
	 * used in determining when uesr actions should be run
	 * @return returns the iteraton count
	 */
	public int getIterationCount()
	{
		return icount;
	}
	/**
	 * returns the user action listener used by this game engine to
	 * fire and interpret user actions
	 * @return returns the user action listener
	 */
	public UserActionListener getUserActionListener()
	{
		return ual;
	}
	/**
	 * performs all the queued user actions
	 * @param iteration the iteration the game engine is on
	 */
	private void performedQueuedUserActions(int iteration)
	{
		ArrayList<UserAction> ua = userActions.get(iteration);
		if(ua != null)
		{
			for(int i = 0; i < ua.size(); i++)
			{
				interpretUserAction(ua.get(i));
			}
			userActions.remove(iteration);
		}
		ua = userActions.get(UserAction.RUN_INSTANTLY);
		if(ua != null)
		{
			for(int i = 0; i < ua.size(); i++)
			{
				//System.out.println("interpreting instant action");
				interpretUserAction(ua.get(i));
			}
			userActions.remove(UserAction.RUN_INSTANTLY);
		}
	}
	/**
	 * actually interprets the user actions
	 * @param ua the user action to be interpreted
	 */
	private void interpretUserAction(UserAction ua)
	{
		if(ua.getOwner().getAI() != null)
		{
			if(ua instanceof MouseAction)
			{
				MouseAction mc = (MouseAction)ua;
				if(mc.getType() == MouseAction.press)
				{
					mc.getOwner().getAI().interpretMousePress(mc);
				}
				else if(mc.getType() == MouseAction.release)
				{
					mc.getOwner().getAI().interpretMouseRelease(mc);
				}
				else if(mc.getType() == MouseAction.click)
				{
					mc.getOwner().getAI().interpretMouseClick(mc);
				}
			}
			else if(ua instanceof KeyPress)
			{
				KeyPress kp = (KeyPress)ua;
				kp.getOwner().getAI().interpretKeyPress(kp);
			}
			else if(ua instanceof KeyRelease)
			{
				KeyRelease kr = (KeyRelease)ua;
				kr.getOwner().getAI().interpretKeyRelease(kr);
			}
		}
	}
	/**
	 * runs through the build orders and creates units if dictated by
	 * the build order
	 */
	private void performQueuedBuildOrders(int iteration)
	{
		if(buildOrders.get(iteration) != null)
		{
			Iterator<BuildOrder> i = buildOrders.get(iteration).iterator();
			while(i.hasNext())
			{
				BuildOrder bo = i.next();
				Unit u = EngineConstants.unitFactory.makeUnit(bo.getBuildOrder(), bo.getOwner(), bo.getLocation());
				w.registerElement(u);
			}
			buildOrders.remove(iteration);
		}
	}
	/**
	 * queues a user action to be executed at the correct SGEngine iteration
	 * @param ua the user action to be queued
	 */
	public void queueUserAction(UserAction ua)
	{
		//System.out.println(ua.getName()+" queued");
		if(userActions.get(ua.getRunTime()) != null)
		{
			userActions.get(ua.getRunTime()).add(ua);
		}
		else
		{
			ArrayList<UserAction> al = new ArrayList<UserAction>();
			al.add(ua);
			userActions.put(ua.getRunTime(), al);
		}
	}
	public void queueBuildOrder(BuildOrder bo)
	{
		if(buildOrders.get(bo.getRunTime()) != null)
		{
			buildOrders.get(bo.getRunTime()).add(bo);
		}
		else
		{
			ArrayList<BuildOrder> al = new ArrayList<BuildOrder>();
			al.add(bo);
			buildOrders.put(bo.getRunTime(), al);
		}
		//System.out.println("build order for "+bo.getBuildOrder()+" queued, run time = "+bo.getRunTime()+", current time = "+icount);
	}
	public static void main(String[] args)
	{
		/*System.load(System.getProperty("user.dir")+System.getProperty("file.separator")+"lib"+
				System.getProperty("file.separator")+"jogl_cg.dll");
		System.load(System.getProperty("user.dir")+System.getProperty("file.separator")+"lib"+
				System.getProperty("file.separator")+"gluegen-rt.dll");
		System.load(System.getProperty("user.dir")+System.getProperty("file.separator")+"lib"+
				System.getProperty("file.separator")+"jogl_awt.dll");
		System.load(System.getProperty("user.dir")+System.getProperty("file.separator")+"lib"+
				System.getProperty("file.separator")+"jogl.dll");*/
		new SGEngine();
	}
}
