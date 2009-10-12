package dynamicMap;

import java.util.ArrayList;

public final class Partition
{
	private ArrayList<Region> l = new ArrayList<Region>();
	
	public ArrayList<Region> getElements()
	{
		return l;
	}
	public void addElement(Region e)
	{
		l.add(e);
	}
	public void removeElement(int index)
	{
		l.remove(index);
	}
}
