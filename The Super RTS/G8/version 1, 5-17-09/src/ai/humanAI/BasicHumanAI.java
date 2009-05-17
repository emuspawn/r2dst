package ai.humanAI;

import graphics.GLCamera;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import sgEngine.userAction.*;
import utilities.Location;
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
	GLCamera c;
	boolean unSelect = false; //true if the ai should unselect all units next iteration
	Location press; //where the selector key was pressed (not dragged), where the units are sent to move to
	ArrayList<Polygon> selections = new ArrayList<Polygon>();
	
	Location initialPress; //where the mouse was first pressed (when dragging)
	boolean dragging = false;
	
	public BasicHumanAI(Owner o, World w, GLCamera c)
	{
		super(o, w);
		this.c = c;
	}
	public void drawUI(GLAutoDrawable d)
	{
		double ydiff = c.getLocation().y-c.getViewLocation().y;
		double zdiff = c.getLocation().z-c.getViewLocation().z;
		double yzslope = ydiff / zdiff;
		double height = 1; //height of the selector
		double depth = ((height-c.getLocation().y)/yzslope)+c.getLocation().z;
		
		/*double xdiff = c.getLocation().x - c.getViewLocation().x;
		double zxslope = zdiff / xdiff;
		double xover = ((depth-c.getLocation().z)/zxslope)+c.getLocation().x;*/
		
		Location l = new Location(c.getLocation().x, height, depth);
		
		double width = 10; //the width of the selector
		GL gl = d.getGL();
		gl.glPushMatrix();
		gl.glRotated(-c.getRotation(), 0, 1, 0);
		gl.glColor3d(0, 128, 255);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(l.x-width/2, l.y, l.z-width/2);
		gl.glVertex3d(l.x+width/2, l.y, l.z-width/2);
		gl.glVertex3d(l.x+width/2, l.y, l.z+width/2);
		gl.glVertex3d(l.x-width/2, l.y, l.z+width/2);
		gl.glEnd();
		gl.glPopMatrix();
		
		
		try
		{
			gl.glColor4d(255, 128, 0, 0);
			Location center = c.getMapLocation(1);
			if(dragging)
			{
				gl.glBegin(GL.GL_QUADS);
				gl.glVertex3d(initialPress.x, initialPress.y, initialPress.z);
				gl.glVertex3d(center.x, initialPress.y, initialPress.z);
				gl.glVertex3d(center.x, initialPress.y, center.z);
				gl.glVertex3d(initialPress.x, initialPress.y, center.z);
				gl.glEnd();
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
			if(start.compareTo(end) == 0)
			{
				//selection was not dragged
				press = end;
			}
			else
			{
				//selection dragged, region formed
				selections.add(getSelectionRegion(start, end));
			}
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
	private Polygon getSelectionRegion(Location start, Location end)
	{
		Polygon p = new Polygon();
		if(start.compareTo(end) != 0)
		{
			p.addPoint((int)start.x, (int)start.z);
			p.addPoint((int)start.x, (int)end.z);
			p.addPoint((int)end.x, (int)end.z);
			p.addPoint((int)end.x, (int)start.z);
		}
		else
		{
			p.addPoint((int)start.x-1, (int)start.z-1);
			p.addPoint((int)start.x+1, (int)start.z-1);
			p.addPoint((int)start.x+1, (int)start.z+1);
			p.addPoint((int)start.x-1, (int)start.z+1);
		}
		return p;
	}
	public void interpretMouseClick(MouseClick ma)
	{
		if(ma.isRightClick())
		{
			unSelect = true;
		}
	}
	/**
	 * gets a 2d polygon representing the unit on the screen
	 * @return returns the unit's polygon as seen by the user
	 */
	private Polygon get2DPolygon(Unit u)
	{
		Location l = u.getLocation();
		Location p1 = c.project(new Location(l.x-u.getWidth(), l.y, l.z-u.getHeight()));
		Location p2 = c.project(new Location(l.x+u.getWidth(), l.y, l.z-u.getHeight()));
		Location p3 = c.project(new Location(l.x+u.getWidth(), l.y, l.z+u.getHeight()));
		Location p4 = c.project(new Location(l.x-u.getWidth(), l.y, l.z+u.getHeight()));
		Polygon p = new Polygon();
		p.addPoint((int)p1.x, (int)p1.y);
		p.addPoint((int)p2.x, (int)p2.y);
		p.addPoint((int)p3.x, (int)p3.y);
		p.addPoint((int)p4.x, (int)p4.y);
		return p;
	}
	/**
	 * gets a 3d polygon representing the unit in the world
	 * @return returns the unit's polygon
	 */
	private Polygon get3DPolygon(Unit u)
	{
		Location l = u.getLocation();
		Location p1 = new Location(l.x-u.getWidth(), l.y, l.z-u.getDepth());
		Location p2 = new Location(l.x+u.getWidth(), l.y, l.z-u.getDepth());
		Location p3 = new Location(l.x+u.getWidth(), l.y, l.z+u.getDepth());
		Location p4 = new Location(l.x-u.getWidth(), l.y, l.z+u.getDepth());
		Polygon p = new Polygon();
		p.addPoint((int)p1.x, (int)p1.z);
		p.addPoint((int)p2.x, (int)p2.z);
		p.addPoint((int)p3.x, (int)p3.z);
		p.addPoint((int)p4.x, (int)p4.z);
		return p;
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
					Iterator<Polygon> pi = selections.iterator();
					while(pi.hasNext())
					{
						Polygon p = pi.next();
						if(get3DPolygon(u).intersects(p.getBounds2D()))
						{
							u.setSelected(true);
							//System.out.println("unit selected!");
							break;
						}
					}
					if(press != null && get3DPolygon(u).contains(press.x, press.z) && !unitPressSelected)
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
					orderUnit(u, press);
				}
				else
				{
					orderUnit(u, null);
				}
			}
		}
		selections = new ArrayList<Polygon>();
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
