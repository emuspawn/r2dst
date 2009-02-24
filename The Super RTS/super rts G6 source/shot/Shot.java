package shot;

import utilities.Location;
import utilities.MoverseV3;
import owner.Owner;
import java.awt.Color;
import java.awt.Graphics;
import graphics.Camera;
import java.awt.Point;

public class Shot
{
	Location start;
	Location end;
	int movement = 9;
	double maxDistance;
	int distanceTraveled = 0;
	boolean dead = false;
	Owner o;
	int damage;
	int shotSize = 3;
	
	public Shot(Owner o, Location start, Location end, int damage)
	{
		maxDistance = start.distanceTo(end)+start.distanceTo(end)*.4;
		this.o = o;
		this.start = start;
		this.end = end;
		this.damage = damage;
	}
	public void updateShot()
	{
		start = MoverseV3.getNewLocation(start, end, movement);
		distanceTraveled += movement;
		if(distanceTraveled >= maxDistance) //|| (start.x == end.x && start.y == end.y))
		{
			//System.out.println("shot = dead, "+distanceTraveled+" >= "+maxDistance);
			dead = true;
		}
	}
	public int getDamage()
	{
		return damage;
	}
	public Location getLocation()
	{
		return start;
	}
	public Owner getOwner()
	{
		return o;
	}
	public void drawShot(Graphics g, Camera c)
	{
		Point p = c.getScreenLocation(start);
		g.setColor(Color.black);
		g.fillRect(p.x, p.y, shotSize, shotSize);
	}
}
