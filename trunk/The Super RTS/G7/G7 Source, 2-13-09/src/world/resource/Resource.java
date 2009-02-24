package world.resource;

import graphics.Camera;
import world.unit.Unit;
import utilities.Location;
import java.awt.Graphics2D;
import java.awt.Color;
import world.spawn.*;
import world.spawn.spawnFormat.SpawnFormat;
import world.World;

public abstract class Resource extends Unit implements Spawner
{
	Color c;
	boolean visible = true; //if loaded, not visible
	SpawnFormat sf;
	
	public Resource(Location location, String name, int maxLife, Color c, int size, int mass)
	{
		super(null, location, name, maxLife, 0, 0, 0, 0, size);
		this.c = c;
		harvestable = true;
		this.mass = mass;
	}
	public void updateCounter(World w)
	{
		if(sf != null)
		{
			sf.updateCounter(w);
		}
	}
	public void setSpawnFormat(SpawnFormat sf)
	{
		this.sf = sf;
	}
	public void setVisible(boolean setter)
	{
		visible = setter;
	}
	public void drawElement(Graphics2D g, Camera c)
	{
		Location location = getLocation();
		int size = getWidth();
		String name = getName();
		
		g.setColor(this.c);
		int x = c.getScreenLocation(location).x;
		int y = c.getScreenLocation(location).y;
		g.fillOval(x-size/2, y-size/2, size, size);
		String sub = name.substring(0, 3);
		g.setColor(Color.black);
		g.drawString(sub, x-9, y);
	}
}