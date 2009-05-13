package units;

import java.awt.Graphics;
import java.util.ArrayList;

import weapons.Shot;
import weapons.ShotEngine;

public class UnitEngine
{
	ArrayList<Unit> units;
	ShotEngine se;
	
	public UnitEngine(ShotEngine se)
	{
		units = new ArrayList<Unit>();
		this.se = se;
	}
	
	public void addUnit(Unit u)
	{
		units.add(u);
	}
	
	public void removeUnit(int index)
	{
		units.remove(index);
	}
	
	public void performUnitActions()
	{
		for (int i = 0; i < units.size(); i++)
		{
			Unit u = units.get(i);
			u.act();
			if (u.getLife() <= 0)
				units.remove(i);
		}
	}
	
	public void drawUnits(Graphics g)
	{
		for (int i = 0; i < units.size(); i++)
		{
			units.get(i).draw(g);
		}
	}
	
	public void checkUnitCollisions()
	{
		for (int i = 0; i < units.size(); i++)
		{
			int r = units.get(i).getWidth()/2;
			Unit u = units.get(i);
			for (int j = 0; j < se.shots.size(); j++)
			{
				Shot s = se.getShot(j);
				if (Math.abs(u.getX() - s.getX()) < r && Math.abs(u.getY() - s.getY()) < r)
				{
					u.setLife(u.getLife() - s.damage);
					se.removeShot(j);
				}
			}
		}
	}
}
