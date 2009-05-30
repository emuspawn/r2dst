package ai.humanAI.basicHumanAI;

import graphics.GLCamera;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.sun.opengl.util.j2d.TextRenderer;

import sgEngine.EngineConstants;
import sgEngine.SGEngine;
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
 * l = selection key
 * j = deselect key
 * i = movement key
 * c = toggle camera mode
 * 
 * clicking the selection key on a unit selects that unit, if several
 * units are on top of each other, only one unit is selected
 * 
 * movement orders are passed from where 'i' is clicked
 * 
 * dragging the selection keys creates a region that selects all friendly
 * units underneath it
 * 
 * pressing the deselect key deselects all friendly units
 * 
 * @author Jack
 *
 */
public abstract class BasicHumanAI2 extends AI
{
	private static final double selectorSize = 2; //the width of the selector (used for when things are clicked)
	
	GLCamera c;
	boolean unSelect = false; //true if the ai should unselect all units next iteration
	Prism press; //where the selector key was pressed (not dragged), where the units are sent to move to
	ArrayList<Prism> pselections = new ArrayList<Prism>();
	
	Location initialPress; //where the mouse was first pressed (when dragging)
	boolean dragging = false;
	
	TextRenderer tr;
	SGEngine sge;
	
	public BasicHumanAI2(Owner o, World w, SGEngine sge, GLCamera c)
	{
		super(o, w, sge);
		this.c = c;
		this.sge = sge;
		
		Font font = new Font("SansSerif", Font.BOLD, 12);
		tr = new TextRenderer(font, true, false);
	}
	public void drawUI(GLAutoDrawable d)
	{
		GL gl = d.getGL();
		
		try
		{
			gl.glColor4d(255, 128, 0, 0);
			Location ml = sge.getUserActionListener().getMouseDragLocation();
			if(dragging && ml != null)
			{
				Location l = c.getMapLocation(ml, 1);
				//System.out.println(ml);
				getPrismSelectionRegion(initialPress, l).drawPrism(gl);
				new Prism(new Location(l.x, l.y+3, l.z), 6, 6, 6).drawPrism(gl);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("exception caught, continuing normally...");
		}
		drawResources();
		
	}
	private void drawResources()
	{
		tr.beginRendering((int)c.getWidth(), (int)c.getHeight());
		tr.setColor(255, 0, 0, 255);
		tr.draw("Energy: "+o.getEnergy(), (int)(c.getWidth()-110), (int)(c.getHeight()-20));
		tr.draw("Metal: "+o.getMetal(), (int)(c.getWidth()-110), (int)(c.getHeight()-40));
		tr.endRendering();
	}
	public void interpretKeyPress(KeyPress kp)
	{
		if(kp.getCharacter() == 'c')
		{
			EngineConstants.cameraMode = !EngineConstants.cameraMode;
		}
	}
	public void interpretMousePress(MouseAction mc)
	{
		initialPress = mc.getLocation();
		dragging = true;
	}
	public void interpretMouseRelease(MouseAction mc)
	{
		Location start = initialPress;
		Location end = mc.getLocation();
		dragging = false;
		sge.getUserActionListener().nullMouseDragLocation();
		if(start.compareTo(end) != 0)
		{
			pselections.add(getPrismSelectionRegion(start, end));
		}
	}
	public void interpretKeyRelease(KeyRelease kr){}
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
		double height = 10;
		double depth = Math.abs(start.z-end.z);
		Location middle = new Location(start.x+(end.x-start.x)/2, start.y+(end.y-start.y)/2+height/2, start.z+(end.z-start.z)/2);
		return new Prism(middle, width, height, depth);
	}
	public void interpretMouseClick(MouseAction mc)
	{
		if(mc.isRightClick())
		{
			unSelect = true;
		}
		else
		{
			press = new Prism(mc.getLocation(), selectorSize, selectorSize, selectorSize);
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
		
		//selects or delsects units
		while(i.hasNext())
		{
			Unit u = i.next();
			if(!unSelect)
			{
				if(!u.isSelected())
				{
					//selects units based off mouse drags
					Iterator<Prism> pi = pselections.iterator();
					while(pi.hasNext())
					{
						Prism p = pi.next();
						if(u.intersects(p))
						{
							u.setSelected(true);
							break;
						}
					}
					//selects units based off the mouse click
					if(press != null && u.intersects(press) && !unitPressSelected)
					{
						//unit selected by the key press (not drag), press selecting only selects one unit
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
					//press is an order
					Location l = null;
					if(press != null)
					{
						l = press.getLocation();
					}
					orderUnit(u, l);
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
