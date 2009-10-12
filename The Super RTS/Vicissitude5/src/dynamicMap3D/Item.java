package dynamicMap3D;

import utilities.Prism;

public class Item
{
	private int id;
	private Prism p;
	
	public Item(Prism p, int id)
	{
		this.p = p;
		this.id = id;
	}
	public int getID()
	{
		return id;
	}
	public Prism getPrism()
	{
		return p;
	}
}
