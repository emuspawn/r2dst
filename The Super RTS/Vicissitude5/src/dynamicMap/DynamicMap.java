package dynamicMap;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;
import utilities.Location;
import java.awt.Point;
import graphics.Camera;
import java.awt.Rectangle;

/**
 * sorts regions
 * 
 * version 1
 * 
 * @author Jack
 *
 */
public final class DynamicMap
{
	private Partition[][] e;
	private int partitionSize = 50;
	private int width;
	private int height;
	
	/**
	 * constructs a DynamicMap of the passed size
	 * @param width the width of the map
	 * @param height the height of the map
	 */
	public DynamicMap(int width, int height)
	{
		setSize(width, height);
	}
	/**
	 * gets the height
	 * @return returns the width of the map
	 */
	public int getWidth()
	{
		return width;
	}
	/**
	 * tests to see if the element migrated into a different partition, if
	 * it did then it is removed and re-added to the correct partition
	 * @param element the element being tested
	 * @param start the elements initial location (prior to movement)
	 * @param end the elements end location
	 */
	public void adjustElement(Region element, Location start, Location end)
	{
		//tests to see if the element migrated into a different partition
		int x1 = (int)start.x/partitionSize;
		int y1 = (int)start.y/partitionSize;
		int x2 = (int)end.x/partitionSize;
		int y2 = (int)end.y/partitionSize;
		if(x1 != x2 || y1 != y2)
		{
			//System.out.println(x1+", "+y1);
			ArrayList<Region> elements = e[x1][y1].getElements();
			for(int i = elements.size()-1; i >= 0; i--)
			{
				if(elements.get(i).equals(element))
				{
					e[x1][y1].removeElement(i);
					addElement(element);
					//System.out.println("boundary crossed, element removed and added (DynamicMap adjustElement)");
					break;
				}
			}
		}
	}
	/**
	 * checks to see if the passed Rectangle intersects
	 * with any other element in the map
	 * @param r the boundary of the unit being checked for intersection
	 * @return returns true if the element intersects any other element, false otherwise
	 */
	public boolean checkIntersection(Rectangle r)
	{	
		int x = r.x/partitionSize;
		int y = r.y/partitionSize;
		for(int i = (r.x+r.width)/partitionSize; i >= x; i--)
		{
			for(int a = (r.y+r.height)/partitionSize; a >= y; a--)
			{
				ArrayList<Region> elements = e[i][a].getElements();
				for(int q = elements.size()-1; q >= 0; q--)
				{
					if(elements.get(q).getBounds().intersects(r))
					{
						//System.out.println("intersects with "+elements.get(q).getName());
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * removes an element
	 * @param element the element to be removed
	 */
	public void removeElement(Region element)
	{
		//removes the given Region 'element'
		int x = (int)element.getLocation().x/partitionSize;
		int y = (int)element.getLocation().y/partitionSize;
		ArrayList<Region> elements = e[x][y].getElements();
		for(int i = elements.size()-1; i >= 0; i--)
		{
			try
			{
				if(elements.get(i).equals(element))
				{
					e[x][y].removeElement(i);
					break;
				}
			}
			catch(IndexOutOfBoundsException e){}
		}
	}
	/**
	 * gets the height
	 * @return returns the height of the map
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * sets the partitions size, adjusts the map afterwards to accomodate changes
	 * @param setter the new partition size
	 */
	public void setPartitionSize(int setter)
	{
		partitionSize = setter;
		adjustMap();
	}
	/**
	 * sets the map's size
	 * @param width the new width of the map
	 * @param height the new height of the map
	 */
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		adjustMap();
	}
	/**
	 * draws a grid representing the map's partitions
	 * @param g the draw graphics
	 * @param c the camera
	 */
	public void drawPartitionGrid(Graphics2D g, Camera c)
	{
		g.setColor(Color.red);
		for(int x = 0; x <= width; x+=partitionSize)
		{
			for(int y = 0; y <= height; y+=partitionSize)
			{
				g.drawLine(0, y-c.getyover(), c.getWidth(), y-c.getyover());
				g.drawLine(x-c.getxover(), 0, x-c.getxover(), c.getHeight());
			}
		}
	}
	/**
	 * gets all elements that are visible to the camera
	 * @param c the camera
	 * @return returns a list of all visible elements
	 */
	public ArrayList<Region> getVisibleElements(Camera c)
	{
		ArrayList<Region> l = new ArrayList<Region>();
		ArrayList<Region> temp;
		for(int i = (c.getxover()+c.getWidth())/partitionSize; i >= c.getxover()/partitionSize; i--)
		{
			for(int a = (c.getyover()+c.getHeight())/partitionSize; a >= c.getyover()/partitionSize; a--)
			{
				try
				{
					temp = e[i][a].getElements();
					for(int q = temp.size()-1; q >= 0; q--)
					{
						try
						{
							if(temp.get(q).isDead())
							{
								e[i][a].getElements().remove(q);
							}
							else if(c.isOnScreen(temp.get(q).getBounds()))
							{
								l.add(temp.get(q));
							}
						}
						catch(Exception e){}
						/*
						 * the exception must be caught because other
						 * threads can modify the array list as this
						 * method goes through the for loop thus it
						 * may use an index that does not exist
						 */
					}
				}
				catch(ArrayIndexOutOfBoundsException e){}
			}
		}
		return l;
	}
	private void adjustMap()
	{
		//adjust the map to follow new dimension or partition size specifications
		ArrayList<Region> l = null;
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
	/**
	 * gets an element
	 * @param p the location at which the element is returned
	 * @return returns the element at the passed location
	 */
	public Region getElement(Point p)
	{
		int x = p.x/partitionSize;
		int y = p.y/partitionSize;
		ArrayList<Region> l = e[x][y].getElements();
		for(int i = l.size()-1; i >= 0; i--)
		{
			if(l.get(i).getLocation().x == p.x && l.get(i).getLocation().y == p.y)
			{
				return l.get(i);
			}
		}
		return null;
	}
	/**
	 * removes an element from the map
	 * @param p the location at which the element is removed from
	 * @return returns the removed element
	 */
	public Region removeElement(Point p)
	{
		return removeElement(new Location(p.x, p.y));
	}
	/**
	 * removes an element from the map
	 * @param p the location at which the element is removed from
	 * @return returns the removed element
	 */
	public Region removeElement(Location p)
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
	private Region removeElement(Location p, int x, int y)
	{
		/*
		 * removes an element that is in the specified matrix position, [x][y]
		 * and at location 'p', used in order to adjust elements that have been
		 * moved
		 */
		ArrayList<Region> l = e[x][y].getElements();
		for(int i = l.size()-1; i >= 0; i--)
		{
			if(l.get(i).getLocation().x == p.x && l.get(i).getLocation().y == p.y)
			{
				return l.remove(i);
			}
		}
		return null;
	}
	/**
	 * adds an element to the map, puts it in the proper partition automatically
	 * @param element the element to be added
	 */
	public void addElement(Region element)
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
	/**
	 * checks to see if a given location resides in the map
	 * @param l the location to be tested
	 * @return returns whether or not the location passed is in the map
	 */
	public boolean inMap(Location l)
	{
		if(l.x <= width && l.x >= 0 && l.y <= height && l.y >= 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * gets all the maps elements
	 * @return returns all elements in the map
	 */
	public ArrayList<Region> getElements()
	{
		ArrayList<Region> l = new ArrayList<Region>();
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
