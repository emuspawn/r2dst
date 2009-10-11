package gameEngine.world;

import java.io.*;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import gameEngine.StartSettings;
import gameEngine.ai.AI;
import gameEngine.world.owner.Owner;
import gameEngine.world.shot.Shot;
import gameEngine.world.shot.ShotEngine;
import gameEngine.world.unit.Unit;
import gameEngine.world.unit.UnitEngine;
import javax.media.opengl.GL;
import mapEditor.Map;
import pathFinder.*;
import pathFinder.epfv2.EPFV2;
import utilities.Location;
import utilities.Polygon;
import utilities.Region;

/**
 * holds information about the game world, presents a front that routes game information
 * to wherever it needs to be via the register method (shots automatically sent to shot engine,
 * units automatically sent to unit engine)
 * @author Jack
 *
 */
public class World
{
	ShotEngine se;
	UnitEngine ue;
	Owner[] o;
	Polygon[] p = new Polygon[10];
	PathFinder pf;
	
	int width;
	int height;
	
	public World(StartSettings ss)
	{
		width = ss.getMapWidth();
		height = ss.getMapHeight();
		
		/*for(int i = 0; i < p.length; i++)
		{
			double x = Math.random()*width;
			double y = Math.random()*height;
			//Location[] vertices = {new Location(x, y), new Location(x+Math.random()*70, y), new Location(x, y+Math.random()*80)};
			Location[] vertices = {new Location(x, y), new Location(x, y+Math.random()*80), 
					new Location(x+Math.random()*90, y+Math.random()*120), new Location(x+Math.random()*70, y)};
			//p[i] = new Polygon(new Location(x, y), vertices);
			p[i] = new Polygon(Polygon.determineBoundingRegion(vertices), vertices);
		}*/
		Map m = new Map();
		try
		{
			File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+
					System.getProperty("file.separator")+"maps"+System.getProperty("file.separator")+
					"spiral.map");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			m.readMap(dis);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		p = new Polygon[m.getPolygons().size()];
		System.out.println(p.length);
		for(int i = 0; i < m.getPolygons().size(); i++)
		{
			p[i] = m.getPolygons().get(i);
		}
		
		se = new ShotEngine(ss, p);
		ue = new UnitEngine(ss, m);
		
		
		this.o = ss.getOwners();
		
		pf = new EPFV2(width, height, p);
	}
	public PathFinder getPathFinder()
	{
		return pf;
	}
	public UnitEngine getUnitEngine()
	{
		return ue;
	}
	public int getMapWidth()
	{
		return width;
	}
	public int getMapHeight()
	{
		return height;
	}
	/**
	 * tests to see if the passed location is inside the game world
	 * @param x
	 * @param y
	 * @return returns true if the passed location is in the game world,
	 * false otherwise
	 */
	public boolean inWorld(double x, double y)
	{
		return new Region(0, 0, width, height).contains(x, y);
	}
	/**
	 * draws the world
	 * @param x x position of the bottom left corner of the screen in game space
	 * @param y y position of the bottom left corner of the screen in game space
	 * @param dwidth width of the displayed region of the screen
	 * @param dheight height of the displayed region of the screen
	 * @param gl
	 */
	public void drawWorld(double x, double y, int dwidth, int dheight, GL gl)
	{
		HashSet<Region> r;
		Iterator<Region> i;
		try
		{
			r = ue.getAllUnits().getIntersections(new Region(x, y, dwidth, dheight));
			i = r.iterator();
			while(i.hasNext())
			{
				Unit u = (Unit)i.next();
				u.drawUnit(gl);
			}
		}
		catch(ConcurrentModificationException e){}
		try
		{
			r = se.getShots().getIntersections(new Region(x, y, dwidth, dheight));
			i = r.iterator();
			while(i.hasNext())
			{
				Shot s = (Shot)i.next();
				s.drawShot(gl);
				if(s.isDead())
				{
					//System.out.println("dead and should not be drawn...");
				}
			}
			//se.getShotPartition().drawPartition(gl, width, height);
		}
		catch(ConcurrentModificationException e){}
		

		gl.glLineWidth(1);
		for(int a = 0; a < p.length; a++)
		{
			gl.glColor4d(0, 1, 0, .6);
			p[a].drawPolygon(gl, .1);
			/*gl.glColor3d(1, 0, 0);
			p[a].drawNormalVectors(gl, 10, .1);
			p[a].drawRegion(gl);*/
		}
		
		//gl.glLineWidth(1);
		gl.glColor4d(1, 1, 1, .3);
		pf.drawPathing(gl, false);
		
		/*double depth = -1;
		gl.glColor3d(0, 1, 0);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(x, y, depth);
		gl.glVertex3d(x+dwidth, y, depth);
		gl.glVertex3d(x+dwidth, y+dheight, depth);
		gl.glVertex3d(x, y+dheight, depth);
		gl.glEnd();*/
		
		/*try
		{
			Iterator<Owner> oi = ue.getUnits().keySet().iterator();
			while(oi.hasNext())
			{
				Owner owner = oi.next();
				double[] c = owner.getColor();
				gl.glColor3d(c[0], c[1], c[2]);
				ue.getUnits().get(owner).drawPartition(gl, dwidth, dheight);
			}
		}
		catch(Exception e){}*/
		gl.glColor3d(1, 0, 0);
		//ue.getAllUnits().drawPartition(gl, dwidth, dheight);
		
		gl.glClearColor(0, 0, 0, 1);
	}
	public void updateWorld(double tdiff, HashMap<Owner, AI> ais)
	{
		for(int i = o.length-1; i >= 0; i--)
		{
			ais.get(o[i]).performAIFunctions();
		}
		ue.updateUnitEngine(tdiff, se);
		se.updateShotEngine(tdiff, ue);
		
		/*double tdiff = (System.currentTimeMillis()-elapsed)/1000.0;
		if(elapsed == 0)
		{
			tdiff = 0;
		}
		for(int i = o.length-1; i >= 0; i--)
		{
			ais.get(o[i]).performAIFunctions();
		}
		ue.updateUnitEngine(tdiff, se);
		se.updateShotEngine(tdiff, ue);
		elapsed = System.currentTimeMillis();*/
	}
}
