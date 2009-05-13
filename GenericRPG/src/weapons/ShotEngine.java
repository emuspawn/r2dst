package weapons;

import java.awt.Graphics;
import java.util.ArrayList;

public class ShotEngine
{
	public ArrayList<Shot> shots;
	
	public ShotEngine()
	{
		shots = new ArrayList<Shot>();
	}
	
	public Shot getShot(int index)
	{
		return shots.get(index);
	}
	
	public void addShot(Shot s)
	{
		shots.add(s);
	}
	
	public void removeShot(int index)
	{
		shots.remove(index);
	}
	
	public void moveShots()
	{
		int i = 0;
		while (i < shots.size())
		{
			boolean dead = shots.get(i).move();
			if (dead)
			{
				shots.remove(i);
			}
			else
			{
				i++;
			}
		}
	}
	
	public void drawShots(Graphics g)
	{
		for (int i = 0; i < shots.size(); i++)
		{
			shots.get(i).draw(g);
		}
	}
}
