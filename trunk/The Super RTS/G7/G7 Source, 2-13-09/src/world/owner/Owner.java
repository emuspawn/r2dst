package world.owner;

import java.awt.Color;
import utilities.Location;
import world.BuildEngine;
import world.unit.UnitEngine;

public class Owner
{
	private String name;
	private Color c;
	
	int mass = 0;
	
	int unitCount = 0;
	int currentUnitMax = 0; //where the pop cap is for the owner
	String ai = new String(); //the ai the owner is user
	Location startingLocation;
	
	public Owner(String name, Color c, Location startingLocation)
	{
		this.name = name;
		this.c = c;
		this.startingLocation = startingLocation;
	}
	public void lowerMass(int setter)
	{
		mass -= setter;
	}
	public void setAIUsing(String s)
	{
		ai = s;
	}
	public Location getStartingLocation()
	{
		return startingLocation;
	}
	public String getAIUsing()
	{
		return ai;
	}
	public void setCurrentUnitMax(Object o, int setter)
	{
		if(o.getClass() == BuildEngine.class || o.getClass() == UnitEngine.class)
		{
			currentUnitMax = setter;
		}
	}
	public int getCurrentUnitMax()
	{
		return currentUnitMax;
	}
	public Color getColor()
	{
		return c;
	}
	public String getName()
	{
		return name;
	}
	public int getUnitCount()
	{
		return unitCount;
	}
	public void setUnitCount(Object o, int setter)
	{
		if(o.getClass() == BuildEngine.class || o.getClass() == UnitEngine.class)
		{
			unitCount = setter;
		}
	}
	public int getMass()
	{
		return mass;
	}
	public void setMass(int setter)
	{
		mass = setter;
	}
}
