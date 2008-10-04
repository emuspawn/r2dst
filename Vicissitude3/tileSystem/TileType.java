package tileSystem;

import java.awt.Color;

public class TileType
{
	String name; //the name of the type
	int type;
	Color c;
	boolean impassable;
	
	public TileType(int type, String name, Color c, boolean impassable)
	{
		this.type = type;
		this.name = name;
		this.c = c;
		this.impassable = impassable;
	}
	public boolean getImpassable()
	{
		return impassable;
	}
	public int getType()
	{
		return type;
	}
	public Color getColor()
	{
		return c;
	}
	public String getName()
	{
		return name;
	}
}
