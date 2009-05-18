package world.shot.weapon;

import java.util.HashSet;
import java.util.Iterator;

import javax.media.opengl.GL;

import dynamicMap.Region;
import dynamicMap3D.DynamicMap3D;

import utilities.Location;
import utilities.Prism;
import world.Element;
import world.owner.Owner;
import world.shot.Shot;
import world.shot.ShotEngine;
import world.unit.Unit;

/**
 * defines a unit's weapon
 * @author Jack
 *
 */
public class Weapon
{
	String name;
	Shot s;
	double range;
	int reload; //iterations between firing
	int reloadCount = 0; //counts up until it reaches the reload
	boolean fired = false;
	
	/**
	 * creates a weapon
	 * @param name the name of the weapon
	 * @param s the type of shot the weapon fires
	 * @param range the range of the weapon
	 * @param reload the reload time of the weapon
	 */
	public Weapon(String name, Shot s, double range, int reload)
	{
		this.name = name;
		this.s = s;
		this.range = range;
		this.reload = reload;
	}
	/**
	 * first checks to see if the weapon can fire, if it can it then gets a
	 * hash set representing all units in range, next it iterates over the set
	 * unitl it finds a unit that is not of the same owner as the weapon, then
	 * it fires a shot from its location to that unit's location
	 * @param location the location of the weapon
	 * @param se
	 * @param unitMap
	 */
	public void fireWeapon(Location location, ShotEngine se, DynamicMap3D unitMap, Owner owner)
	{
		//System.out.println(owner.getName());
		if(!fired)
		{
			//HashSet<Prism> hs = new HashSet<Prism>();]
			HashSet<Prism> hs = unitMap.getElementsInRange(location, range);
			if(hs.size() > 0)
			{
				Iterator<Prism> i = hs.iterator();
				while(i.hasNext() && !fired)
				{
					Element e = (Element)i.next();
					if(!e.getOwner().getName().equalsIgnoreCase(owner.getName()))
					{
						se.registerShot(new Shot(location, e.getLocation(), s, owner));
						fired = true;
						reloadCount = 0;
						//System.out.println("weapon fired");
					}
				}
			}
		}
	}
	public boolean isFired()
	{
		return fired;
	}
	/**
	 * updates the weapon
	 */
	public void updateWeapon()
	{
		if(fired)
		{
			reloadCount++;
			if(reloadCount == reload)
			{
				fired = false;
				//System.out.println("weapon reloaded");
			}
		}
	}
	public void drawWeapon(GL gl)
	{
		gl.glColor3d(128, 128, 128);
		
		gl.glPushMatrix();
		
		if(direction != null)
		{
			double xzangle = 5;
			gl.glRotated(xzangle, 0, 1, 0);
		}
		
		gl.glScaled(2, 2, 2);
		gl.glBegin(GL.GL_TRIANGLES);
		
		//face 1
		gl.glVertex3d(location.x, location.y, location.z+2);
		gl.glVertex3d(location.x+1, location.y, location.z);
		gl.glVertex3d(location.x, location.y+1.5, location.z);
		
		//face 2
		gl.glVertex3d(location.x, location.y, location.z+2);
		gl.glVertex3d(location.x-1, location.y, location.z);
		gl.glVertex3d(location.x, location.y+1.5, location.z);
		
		//face 3
		gl.glVertex3d(location.x, location.y, location.z-1);
		gl.glVertex3d(location.x-1, location.y, location.z);
		gl.glVertex3d(location.x, location.y+1.5, location.z);
		
		//face 4
		gl.glVertex3d(location.x, location.y, location.z-1);
		gl.glVertex3d(location.x+1, location.y, location.z);
		gl.glVertex3d(location.x, location.y+1.5, location.z);
		
		gl.glEnd();
	}
}
