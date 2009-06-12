package world;

import engine.PhysicsEngine;
import force.Gravity;
import graphics.GLCamera;
import java.util.ArrayList;
import world.shot.ShotEngine;
import world.unit.Unit;
import world.unit.UnitEngine;

/**
 * the world that the game runs in
 * @author Jack
 *
 */
public class World
{
	UnitEngine ue;
	ShotEngine se;
	PhysicsEngine pe;
	
	int width;
	int height;
	int depth;
	
	double start = 0;
	
	public World(int width, int height, int depth)
	{
		this.width = width;
		this.height = height;
		this.depth = depth;
		se = new ShotEngine(this);
		ue = new UnitEngine(this, se);
		
		
		pe = new PhysicsEngine();
		pe.registerForce(new Gravity());
	}
	public ShotEngine getShotEngine()
	{
		return se;
	}
	public UnitEngine getUnitEngine()
	{
		return ue;
	}
	public PhysicsEngine getPhysicsEngine()
	{
		return pe;
	}
	public void performWorldFunctions()
	{
		
		ue.performUnitFunctions();
		se.performShotFunctions();
		
		double diff = System.currentTimeMillis()-start;
		if(start != 0)
		{
			pe.updateEngine(diff/1000);
		}
		start = System.currentTimeMillis();
	}
	public ArrayList<Element> getElements(GLCamera c)
	{
		ArrayList<Element> e = new ArrayList<Element>();
		
		try
		{
			e.addAll(ue.getAllUnits());
			
		}
		catch(Exception a)
		{
			a.printStackTrace();
		}
		
		return e;
	}
	/**
	 * gets the width of the world
	 * @return returns the width of the world
	 */
	public int getWidth()
	{
		return width;
	}
	/**
	 * gets the depth of the world
	 * @return returns the depth of the world
	 */
	public int getDepth()
	{
		return depth;
	}
	/**
	 * gets the height of the world
	 * @return returns the height of the world
	 */
	public int getHeight()
	{
		return height;
	}
	public void registerElement(Unit u)
	{
		ue.registerUnit(u);
	}
}
