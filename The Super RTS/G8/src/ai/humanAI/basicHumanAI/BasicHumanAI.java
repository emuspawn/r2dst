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
public abstract class BasicHumanAI extends AI
{
	private static final double selectorWidth = 10; //the width of the selector
	
	GLCamera c;
	boolean unSelect = false; //true if the ai should unselect all units next iteration
	Prism press; //where the selector key was pressed (not dragged), where the units are sent to move to
	ArrayList<Prism> pselections = new ArrayList<Prism>();
	
	Location initialPress; //where the mouse was first pressed (when dragging)
	boolean dragging = false;
	
	Location movementLocation; //determined from the camera's location when 'i' is pressed
	
	TextRenderer tr;
	
	public BasicHumanAI(Owner o, World w, SGEngine sge, GLCamera c)
	{
		super(o, w, sge);
		this.c = c;
		
		Font font = new Font("SansSerif", Font.BOLD, 12);
		tr = new TextRenderer(font, true, false);
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
		
		double ydiff = c.getLocation().y-c.getViewLocation().y;
		double zdiff = c.getLocation().z-c.getViewLocation().z;
		double yzslope = ydiff / zdiff;
		double height = 1; //height of the selector
		double depth = ((height-c.getLocation().y)/yzslope)+c.getLocation().z;
		
		Location l = new Location(c.getLocation().x, height, depth);
		new Prism(l, selectorWidth, 2, selectorWidth).drawPrism(gl);
		
		gl.glPopMatrix();
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
		if(kp.getCharacter() == 'l')
		{
			initialPress = kp.getLocation();
			dragging = true;
		}
		else if(kp.getCharacter() == 'j')
		{
			unSelect = true;
		}
		else if(kp.getCharacter() == 'c')
		{
			EngineConstants.cameraMode = !EngineConstants.cameraMode;
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
				press = new Prism(kr.getLocation(), selectorWidth, 2, selectorWidth);
			}
			else
			{
				//selection dragged, region formed
				pselections.add(getPrismSelectionRegion(start, end));
			}
		}
		else if(kr.getCharacter() == 'i')
		{
			movementLocation = kr.getLocation();
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
				if(BasicHumanAIStats.selectorMovesUnits && !unitPressSelected)
				{
					Location l = null;
					if(press != null)
					{
						l = press.getLocation();
					}
					orderUnit(u, l);
				}
				else if(!BasicHumanAIStats.selectorMovesUnits)
				{
					orderUnit(u, movementLocation);
				}
			}
		}
		pselections = new ArrayList<Prism>();
		unSelect = false;
		movementLocation = null;
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
