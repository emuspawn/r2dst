package dynamicMap3D;

import java.util.ArrayList;

public final class Partition
{
	private ArrayList<Item> l = new ArrayList<Item>();
	
	public ArrayList<Item> getElements()
	{
		return l;
	}
	public void addElement(Item e)
	{
		l.add(e);
	}
}
