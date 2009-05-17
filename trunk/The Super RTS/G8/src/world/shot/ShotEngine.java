package world.shot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashSet;
import dynamicMap3D.DynamicMap3D;
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
	}
	public ArrayList<Shot> getShots()
	{
		return shots;
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
			HashSet<Prism> hs = dm3d.checkIntersection(shot);
			Iterator<Prism> ui = hs.iterator(); //unit iterator
			while(ui.hasNext())
			{
				Element u = (Element)ui.next();
				
				if(!u.getOwner().getName().equalsIgnoreCase(shot.getOwner().getName()))
				{
					u.setLife(u.getLife()-shot.getDamage());
					hit = true;
				}
			}
			if(hit)
			{
				i.remove();
				//System.out.println("unit hit");
			}
			else
			{
				shot.setLocation(MoverseV4.getNewLocation(shot.getLocation(), shot.getTarget(), shot.getMovement()));
			}
		}
	}
}
