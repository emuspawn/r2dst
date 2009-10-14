package game;

import utilities.Region;

public class Unit extends Region
{
	double movement = 800;
	//boolean moving = false;
	double oldx;
	double oldy;
	
	public Unit(double x, double y, double width, double height)
	{
		super(x, y, width, height);
	}
	/**
	 * moves the unit in the direction of the passed vector
	 * @param x
	 * @param y
	 * @param tdiff
	 */
	public void moveUnit(double x, double y, double tdiff)
	{
		//System.out.println("unit moved: "+toString());
		//getLocation()[0]+=x*tdiff;
		//getLocation()[1]+=y*tdiff;
		
		oldx = x;
		oldy = y;
		
		setLocation(getLocation()[0]+movement*tdiff*x, getLocation()[1]+movement*tdiff*y);
	}
	public void updateUnit(double tdiff)
	{
		moveUnit(oldx, oldy, tdiff);
	}
}
