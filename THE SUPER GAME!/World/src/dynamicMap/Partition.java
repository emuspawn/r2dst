package dynamicMap;

import java.util.ArrayList;
import world.Element;

public class Partition
{
	private ArrayList<Element> l = new ArrayList<Element>();
	
	public ArrayList<Element> getElements()
	{
		return l;
	}
	public void addElement(Element e)
	{
		l.add(e);
	}
}
