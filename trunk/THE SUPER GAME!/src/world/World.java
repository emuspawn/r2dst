package world;

import io.WorldReader;
import java.util.ArrayList;
import connection.*;
import utilities.Location;
import dynamicMap.DynamicMap;
import world.destructable.Destructable;
import world.permanent.Permanent;
import world.environment.EnvironmentalEffect;
import graphics.Camera;
import java.io.*;
import java.awt.Rectangle;

public class World implements Runnable
{
	ArrayList<Permanent> pe = new ArrayList<Permanent>(); //permanenet elements
	ArrayList<Destructable> de = new ArrayList<Destructable>(); //destructable elements
	ArrayList<EnvironmentalEffect> ee = new ArrayList<EnvironmentalEffect>(); //environmental elements
	User[] u = new User[10];
	
	DynamicMap dm;
	KeyMap km;
	String name = new String("untitled");
	
	public World(boolean loadWorld)
	{
		dm = new DynamicMap(1000, 1000);
		try
		{
			File f = new File(System.getProperty("user.dir")+"\\keyMapV1.km");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			km = new KeyMap(dis);
			
			if(loadWorld)
			{
				//f = new File(System.getProperty("user.dir")+"\\testWorld.wrld");
				//f = new File(System.getProperty("user.dir")+"\\largeTestMap.wrld");
				f = new File(System.getProperty("user.dir")+"\\largeTestMap.wrld");
				fis = new FileInputStream(f);
				dis = new DataInputStream(fis);
				WorldReader.readWorld(this, dis);
			}
		}
		catch(IOException e)
		{
			System.out.println("io exception");
		}
	}
	public void updateUserScreenDimensions(int userIndex, int width, int height)
	{
		u[userIndex].setWidth(width);
		u[userIndex].setHeight(height);
	}
	public int formConnection(String userName)
	{
		try
		{
			File f = new File(System.getProperty("user.dir")+"\\"+userName+".acc");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			int index = registerUser(new User(dis));
			System.out.println("user "+userName+" registered");
			System.out.println("connection formed successfully");
			System.out.println("user index = "+index);
			return index;
		}
		catch(IOException e)
		{
			System.out.println("io exception");
			e.printStackTrace();
		}
		
		return -1;
	}
	private int registerUser(User user)
	{
		//searches through user array and finds an open slot to add the user to
		for(int i = u.length-1; i >= 0; i--)
		{
			if(u[i] == null)
			{
				u[i] = user;
				return i;
			}
		}
		System.out.println("user not registered, world is full");
		return -1;
	}
	private void translateUserUnit(int x, int y, byte owner)
	{
		Rectangle r = u[owner].getUnit().getBounds();
		r.x = r.x+x;
		r.y = r.y+y;
		Camera c = u[owner].getCamera();
		ArrayList<Element> e = dm.getVisibleElements(c);
		boolean conflicts = false;
		for(int i = e.size()-1; i >= 0; i--)
		{
			if(e.get(i).getBounds().intersects(r) && e.get(i).isImpassable())
			{
				conflicts = true;
				r.x = r.x-x;
				r.y = r.y-y;
				break;
			}
		}
		if(!conflicts)
		{
			u[owner].getUnit().translate(x, y);
		}
	}
	public void interpretUserAction(byte b, byte owner)
	{
		String ua = km.getUserAction(b);
		if(ua.equalsIgnoreCase("move up"))
		{
			translateUserUnit(0, -6, owner);
		}
		else if(ua.equalsIgnoreCase("move right"))
		{
			translateUserUnit(6, 0, owner);
		}
		else if(ua.equalsIgnoreCase("move left"))
		{
			translateUserUnit(-6, 0, owner);
		}
		else if(ua.equalsIgnoreCase("move down"))
		{
			translateUserUnit(0, 6, owner);
		}
	}
	public ArrayList<Permanent> getPermanents()
	{
		return pe;
	}
	public ArrayList<Destructable> getDestructables()
	{
		return de;
	}
	public void addPermanent(Permanent p)
	{
		pe.add(p);
		p.setLocation(new Location(p.getLocation().x+13, p.getLocation().y));
		dm.addElement(pe.get(pe.size()-1));
		pe.get(pe.size()-1).setLocation(new Location(p.getLocation().x-13, p.getLocation().y));
	}
	public String getName()
	{
		return name;
	}
	public void setName(String s)
	{
		name = s;
	}
	public int getWidth()
	{
		return dm.getWidth();
	}
	public int getHeight()
	{
		return dm.getHeight();
	}
	public ArrayList<Element> getVisibleElements(Camera c)
	{
		//used by editor
		return dm.getVisibleElements(c);
	}
	public Camera getUserCamera(int userIndex)
	{
		return u[userIndex].getCamera();
	}
	public ArrayList<Element> getVisibleElements(int userIndex)
	{
		//used by single clients or server (server breaks down and sends to network clients)
		return dm.getVisibleElements(u[userIndex].getCamera());
	}
	public DynamicMap getDynamicMap()
	{
		return dm;
	}
	public void run()
	{
		for(;;)
		{
			processElements();
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException a){}
		}
	}
	private void processElements()
	{
		for(int i = de.size()-1; i >= 0; i--)
		{
			proccessEnvironmentalEffects(de.get(i));
			if(de.get(i).isDead())
			{
				de.get(i).destroy(this);
				de.remove(i);
			}
		}
	}
	private void proccessEnvironmentalEffects(Element e)
	{
		for(int i = ee.size()-1; i >= 0; i--)
		{
			ee.get(i).processEffect(e);
		}
	}
}
