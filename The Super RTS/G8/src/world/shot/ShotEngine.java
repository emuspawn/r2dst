package world.shot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.List;

import dynamicMap3D.DynamicMap3D;
import utilities.Location;
import utilities.MoverseV4;
import utilities.Prism;
import world.Element;
import world.World;

public class ShotEngine
{
	LinkedList<Shot> s = new LinkedList<Shot>();
	World w;
	
	//for drawing purposes, to avoid the concurrent modifcation exception
	ArrayList<Shot> shots = new ArrayList<Shot>();
	
	
	public ShotEngine(World w)
	{
		this.w = w;
	}
	public void registerShot(Shot shot)
	{
		s.add(shot);
		//System.out.println("shot registered, owner = "+shot.getOwner().getName());
	}
	public List<Shot> getShots()
	{
		return s;
	}
	public void performShotFunctions()
	{
		updateExistingShots();
	}
	/**
	 * evaluates all existing shots for collisions with units of
	 * an opposing owner
	 */
	private void updateExistingShots()
	{
		DynamicMap3D dm3d = w.getUnitEngine().getUnitMap();
		Iterator<Shot> i = s.iterator();
		while(i.hasNext())
		{
			boolean hit = false; //hit an enemy
			Shot shot = i.next();
			//System.out.println(shot.getOwner().getName());
			HashSet<Prism> hs = dm3d.checkIntersection(shot);
			Iterator<Prism> ui = hs.iterator(); //unit iterator
			while(ui.hasNext())
			{
				Element u = (Element)ui.next();
				
				if(!u.getOwner().getName().equalsIgnoreCase(shot.getOwner().getName()))
				{
					u.setLife(u.getLife()-shot.getDamage());
					//System.out.println(u.getOwner().getName());
					hit = true;
				}
			}
			if(hit)
			{
				i.remove();
				//System.out.println("unit hit, total shots = "+s.size());
			}
			else
			{
				shot.setLocation(MoverseV4.getNewLocation(shot.getLocation(), shot.getTarget(), shot.getMovement()));
				if(shot.getLocation().compareTo(shot.getTarget()) == 0)
				{
					i.remove();
				}
				
				/*Prism worldBounds = new Prism(new Location(0, w.getHeight()/2, 0), w.getWidth(), w.getHeight(), w.getDepth());
				if(!worldBounds.intersects(shot))
				{
					System.out.println("shot out of world, "+shot.getLocation());
				}*/
			}
		}
		//System.out.println(s.size());
	}
}
