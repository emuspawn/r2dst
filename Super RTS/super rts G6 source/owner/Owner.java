package owner;

import java.awt.Color;
import world.BuildEngine;
import world.resource.Resource;
import world.unit.UnitEngine;

public class Owner
{
	private String name;
	private Color c;
	Resource[] gameResources; //all resoures available on the map being played
	int[] stockpile; //index of stockpile is the index in gameResources it represents
	int unitCount = 0;
	int currentUnitMax = 0; //where the pop cap is for the owner
	String ai = new String(); //the ai the owner is user
	
	public Owner(String name, Color c, Resource[] gameResources)
	{
		this.name = name;
		this.c = c;
		this.gameResources = gameResources;
		stockpile = new int[gameResources.length];
	}
	public void setAIUsing(String s)
	{
		ai = s;
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
	public void addResource(Resource r)
	{
		for(int i = 0; i < gameResources.length; i++)
		{
			if(gameResources[i].getName().equalsIgnoreCase(r.getName()))
			{
				stockpile[i]++;
				break;
			}
		}
	}
	public void printResourceCount()
	{
		for(int i = 0; i < stockpile.length; i++)
		{
			System.out.println(stockpile[i]+" ");
		}
		System.out.println();
	}
	public int getResourceCount(String r)
	{
		//returns how much of Resource "r" a player has
		for(int i = 0; i < gameResources.length; i++)
		{
			if(gameResources[i].getName().equalsIgnoreCase(r))
			{
				return stockpile[i];
			}
		}
		return 0; //has none of the given resource "r"
	}
	public void lowerResouceCount(String r, int setter)
	{
		for(int i = 0; i < gameResources.length; i++)
		{
			if(gameResources[i].getName().equalsIgnoreCase(r))
			{
				stockpile[i] -= setter;
			}
		}
	}
}
