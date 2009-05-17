package ai.humanAI;

import graphics.GLCamera;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import sgEngine.userAction.*;
import utilities.Location;
import utilities.Prism;
import world.World;
import world.owner.Owner;
import world.unit.Unit;
import ai.AI;

/**
 * a basic human ai, performs low level calls such as selecting units
 * by mouse click automatically
 * 
 * basic human UI:
 * 
 * commands:
 * l=selection key
 * j=deselect key
 * 
 * clicking the selection key on a unit selects that unit, if several
 * units are on top of each other, only one unit is selected, orders
 * are then given with a null value for the key press location because
 * the press location is not counted as an order
 * 
 * clicking the selection key on a map location that does not have a
 * friendly unit is considered an order, thus, unit orders are passed
 * the location of the key press
 * 
 * dragging the selection keys creates a region that selects all friendly
 * units underneath it
 * 
 * pressing the deselect key deselects all friendly units
 * 
 * @author Jack
 *
 */
public abstract class BasicHumanAI extends AI
{
	private static final double selectorWidth = 10; //the width of the selector
	
	GLCamera c;
	boolean unSelect = false; //true if the ai should unselect all units next iteration
	Prism press; //where the selector key was pressed (not dragged), where the units are sent to move to
	ArrayList<Prism> pselections = new ArrayList<Prism>();
	
	Location initialPress; //where the mouse was first pressed (when dragging)
	boolean dragging = false;
	
	public BasicHumanAI(Owner o, World w, GLCamera c)
	{
		super(o, w);
		this.c = c;
	}
	/**
	 * draws the selector
	 * @param gl
	 */
	private void drawSelector(GL gl)
	{
		gl.glPushMatrix();
		gl.glRotated(-c.getRotation(), 0, 1, 0);
		gl.glColor3d(0, 128, 255);
		getSelectorPrism().drawPrism(gl);
		gl.glPopMatrix();
	}
	/**
	 * gets the prism representig the selector
	 * @return
	 */
	private Prism getSelectorPrism()
	{
		double ydiff = c.getLocation().y-c.getViewLocation().y;
		double zdiff = c.getLocation().z-c.getViewLocation().z;
		double yzslope = ydiff / zdiff;
		double height = 1; //height of the selector
		double depth = ((height-c.getLocation().y)/yzslope)+c.getLocation().z;
		
		Location l = new Location(c.getLocation().x, height, depth);
		
		return new Prism(l, selectorWidth, 2, selectorWidth);
	}
	public void drawUI(GLAutoDrawable d)
	{
		GL gl = d.getGL();
		
		drawSelector(gl);
		
		try
		{
			gl.glColor4d(255, 128, 0, 0);
			Location center = c.getMapLocation(1);
			if(dragging)
			{
				getPrismSelectionRegion(initialPress, center).drawPrism(gl);
			}
		}
		catch(Exception e){}
	}
	public void interpretKeyPress(KeyPress kp)
	{
		if(kp.getCharacter() == 'l')
		{
			initialPress = kp.getLocation();
			dragging = true;
		}
		else if(kp.getCharacter() == 'j')
		{
			unSelect = true;
		}
	}
	public void interpretKeyRelease(KeyRelease kr)
	{
		if(kr.getCharacter() == 'l')
		{
			Location start = initialPress;
			Location end = kr.getLocation();
			dragging = false;
			if(start.compareTo(end) != 0)
			{
				//selection dragged, region formed
				pselections.add(getPrismSelectionRegion(start, end));
			}
		}
		if(kr.getCharacter() == 'i')
		{
			press = getSelectorPrism();
		}
		
	}
	/**
	 * gets the region created by dragging the cursor from the starting
	 * location to the ending location, used in determining which units where
	 * selected
	 * @param start
	 * @param end
	 * @return
	 */
	private Prism getPrismSelectionRegion(Location start, Location end)
	{
		double width = Math.abs(start.x-end.x);
		//double height = Math.abs(start.y-end.y);
		double height = 20;
		double depth = Math.abs(start.z-end.z);
		Location middle = new Location(start.x+(end.x-start.x)/2, start.y+(end.y-start.y)/2, start.z+(end.z-start.z)/2);
		return new Prism(middle, width, height, depth);
	}
	public void interpretMouseClick(MouseClick ma)
	{
		if(ma.isRightClick())
		{
			unSelect = true;
		}
	}
	public void performAIFunctions()
	{
		LinkedList<Unit> units = getUnits();
		Iterator<Unit> i = units.iterator();
		
		/*
		 * the unit was selected by the location stored
		 * in press, means it was not drag selected, if
		 * true then the press location is not included
		 * when commanding units because it was not an
		 * order (it was a selection), if false then it
		 * was an order to be interpreted
		 */
		boolean unitPressSelected = false;
		
		//selects units
		while(i.hasNext())
		{
			Unit u = i.next();
			if(!unSelect)
			{
				if(!u.isSelected())
				{
					Iterator<Prism> pi = pselections.iterator();
					while(pi.hasNext())
					{
						Prism p = pi.next();
						if(u.intersects(p))
						{
							u.setSelected(true);
							//System.out.println("unit selected!");
							break;
						}
					}
					if(press != null && u.intersects(press) && !unitPressSelected)
					{
						//unit selected by the key press (not drag)
						//press selecting only selects one unit
						unitPressSelected = true;
						u.setSelected(true);
					}
				}
			}
			else
			{
				u.setSelected(false);
			}
		}
		i = units.iterator();
		//orders units
		while(i.hasNext())
		{
			Unit u = i.next();
			if(!unSelect)
			{
				if(!unitPressSelected)
				{
					/*
					 * all units selected by dragging, thus, the press location is
					 * the location of a command
					 */
					Location l = null;
					if(press != null)
					{
						l = press.getLocation();
					}
					orderUnit(u, l);
				}
				else
				{
					orderUnit(u, null);
				}
			}
		}
		pselections = new ArrayList<Prism>();
		unSelect = false;
		press = null;
	}
	/**
	 * called once per unit, units commands should be performed here
	 * @param u
	 * @param l the location of the first mouse click that did not
	 * select a unit, mouseClicks.get(0)
	 */
	protected abstract void orderUnit(Unit u, Location l);
}
