package dynamicMap;

import java.util.ArrayList;
import utilities.Location;
import java.awt.Point;
import graphics.Camera;
import world.Element;

public class DynamicMap
{
	private Partition[][] e;
	private int partitionSize = 200;
	private int width;
	private int height;
	
	ArrayList<Element> movableUnits;
	
	public DynamicMap(int width, int height)
	{
		movableUnits = new ArrayList<Element>();
		setSize(width, height);
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public void setPartitionSize(int setter)
	{
		partitionSize = setter;
		adjustMap();
	}
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		adjustMap();
	}
	public void addMovableElement(Element e)
	{
		movableUnits.add(e);
	}
	public ArrayList<Element> getVisibleElements(Camera c)
	{
		ArrayList<Element> l = new ArrayList<Element>();
		l.addAll(movableUnits);
		
		for(int i = (width)/partitionSize; i >= 0; i--)
		{
			for(int a = (height)/partitionSize; a >= 0; a--)
			{
				l.addAll(e[i][a].getElements());
			}
		}
		for(int i = l.size()-1; i >= 0; i--)
		{
			if(!c.getOnScreen(l.get(i).getBounds()))
			{
				l.remove(i);
			}
		}
		return l;
	}
	private void adjustMap()
	{
		//adjust the map to follow new dimension or partition size specifications
		ArrayList<Element> l = null;
		if(e != null)
		{
			l = getElements();
		}
		e = new Partition[(width/partitionSize)+1][(height/partitionSize)+1];
		for(int i = (width/partitionSize); i >= 0; i--)
		{
			for(int a = (height/partitionSize); a >= 0; a--)
			{
				e[i][a] = new Partition();
			}
		}
		if(l != null)
		{
			for(int i = l.size()-1; i >= 0; i--)
			{
				addElement(l.get(i));
			}
		}
	}
	public Element getElement(Point p)
	{
		int x = p.x/partitionSize;
		int y = p.y/partitionSize;
		ArrayList<Element> l = e[x][y].getElements();
		for(int i = l.size()-1; i >= 0; i--)
		{
			if(l.get(i).getLocation().x == p.x && l.get(i).getLocation().y == p.y)
			{
				return l.get(i);
			}
		}
		return null;
	}
	public Element removeElement(Point p)
	{
		if(inMap(new Location(p)))
		{
			int x = p.x/partitionSize;
			int y = p.y/partitionSize;
			ArrayList<Element> l = e[x][y].getElements();
			for(int i = l.size()-1; i >= 0; i--)
			{
				if(l.get(i).getLocation().x == p.x && l.get(i).getLocation().y == p.y)
				{
					return l.remove(i);
				}
			}
		}
		return null;
	}
	public void addElement(Element element)
	{
		if(inMap(element.getLocation()))
		{
			int x = (int)element.getLocation().x/partitionSize;
			int y = (int)element.getLocation().y/partitionSize;
			try
			{
				e[x][y].addElement(element);
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("element outside of map, removed");
			}
		}
	}
	public boolean inMap(Location l)
	{
		if(l.x <= width && l.x >= 0 && l.y <= height && l.y >= 0)
		{
			return true;
		}
		return false;
	}
	public ArrayList<Element> getElements()
	{
		ArrayList<Element> l = new ArrayList<Element>();
		for(int i = e.length-1; i >= 0; i--)
		{
			for(int a = e[i].length-1; a >= 0; a--)
			{
				l.addAll(e[i][a].getElements());
			}
		}
		return l;
	}
}
