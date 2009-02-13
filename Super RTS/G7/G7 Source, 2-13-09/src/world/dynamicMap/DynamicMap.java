package world.dynamicMap;

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
	
	public DynamicMap(int width, int height)
	{
		setSize(width, height);
	}
	public int getWidth()
	{
		return width;
	}
	public void adjustElement(Element element, Location start, Location end)
	{
		//tests to see if the element migrated into a different partition
		int x1 = (int)start.x/partitionSize;
		int y1 = (int)start.y/partitionSize;
		int x2 = (int)end.x/partitionSize;
		int y2 = (int)end.y/partitionSize;
		if(x1 != x2 || y1 != y2)
		{
			ArrayList<Element> elements = e[x1][y1].getElements();
			for(int i = elements.size()-1; i >= 0; i--)
			{
				if(elements.get(i).equals(element))
				{
					e[x1][y1].removeElement(i);
					addElement(element);
					System.out.println("boundary crossed, element removed and added (DynamicMap adjustElement)");
					break;
				}
			}
		}
	}
	public void removeElement(Element element)
	{
		//removes the given Element 'element'
		int x = (int)element.getLocation().x/partitionSize;
		int y = (int)element.getLocation().y/partitionSize;
		ArrayList<Element> elements = e[x][y].getElements();
		for(int i = elements.size()-1; i >= 0; i--)
		{
			if(elements.get(i).equals(element))
			{
				e[x][y].removeElement(i);
				break;
			}
		}
	}
	public int getHeight()
	{
		return height;
	}
	public void setPartitionSize(int setter)
	{
		partitionSize = setter;
	}
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		adjustMap();
	}
	public ArrayList<Element> getVisibleElements(Camera c)
	{
		ArrayList<Element> l = new ArrayList<Element>();
		for(int i = (c.getxover()+c.getWidth())/partitionSize; i >= c.getxover()/partitionSize; i--)
		{
			for(int a = (c.getyover()+c.getHeight())/partitionSize; a >= c.getyover()/partitionSize; a--)
			{
				try
				{
					l.addAll(e[i][a].getElements());
				}
				catch(ArrayIndexOutOfBoundsException e){}
			}
		}
		for(int i = l.size()-1; i >= 0; i--)
		{
			if(!c.isOnScreen(l.get(i).getBounds()))
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
		for(int i = (width/partitionSize)+1-1; i >= 0; i--)
		{
			for(int a = (height/partitionSize)+1-1; a >= 0; a--)
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
		return removeElement(new Location(p.x, p.y));
	}
	public Element removeElement(Location p)
	{
		//removes the element at the Location 'p'
		if(inMap(p))
		{
			int x = (int)(p.x/partitionSize);
			int y = (int)(p.y/partitionSize);
			removeElement(p, x, y);
		}
		return null;
	}
	private Element removeElement(Location p, int x, int y)
	{
		/*
		 * removes an element that is in the specified matrix position, [x][y]
		 * and at location 'p', used in order to adjust elements that have been
		 * moved
		 */
		ArrayList<Element> l = e[x][y].getElements();
		for(int i = l.size()-1; i >= 0; i--)
		{
			if(l.get(i).getLocation().x == p.x && l.get(i).getLocation().y == p.y)
			{
				return l.remove(i);
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
