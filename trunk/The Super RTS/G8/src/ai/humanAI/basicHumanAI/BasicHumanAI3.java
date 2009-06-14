package ai.humanAI.basicHumanAI;

import graphics.GLCamera;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
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
 * orders are not initiated if the UI has been clicked nor are units selected
 * 
 * clicking a unit selects it, if no units are clicked then selected units
 * are given an order to move
 * 
 * dragging the left mouse button selects all units under the mouse drag
 * 
 * right clicking deselects all units
 * 
 * @author Jack
 *
 */
public abstract class BasicHumanAI3 extends AI
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
	
	BuilderDisplay bd;
	
	boolean uiClicked = false;
	
	public BasicHumanAI3(Owner o, World w, SGEngine sge, GLCamera c)
	{
		super(o, w, sge);
		this.c = c;
		this.sge = sge;
		
		Font font = new Font("SansSerif", Font.BOLD, 12);
		tr = new TextRenderer(font, true, true);
		
		bd = new BuilderDisplay(c, sge);
	}
	public void drawUI(GLAutoDrawable d)
	{
		GL gl = d.getGL();
		
		try
		{
			gl.glColor4d(255, 128, 0, 40);
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
		
		bd.drawBuilderDisplay(gl);
		drawBuildingLocations(gl);
	}
	/**
	 * draws small reminders over top of the locations at which buildings
	 * are to be built based off the building location hash map
	 * @param gl
	 */
	private void drawBuildingLocations(GL gl)
	{
		HashMap<String, LinkedList<Location>> m = new HashMap<String, LinkedList<Location>>(bd.getBuildingLocations());
		Iterator<String> i = m.keySet().iterator();
		gl.glColor4d(0, .6, .2, .3);
		//gl.glColor3d(1, 1, 1);
		while(i.hasNext())
		{
			String key = i.next();
			Iterator<Location> lli = m.get(key).iterator();
			while(lli.hasNext())
			{
				//System.out.println("here");
				new Prism(lli.next(), 10, 10, 10).drawPrism(gl);
			}
		}
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
		else if(kp.getCharacter() == 'b')
		{
			bd.setDisplay(!bd.isDisplayed());
		}
	}
	public void interpretMousePress(MouseAction ma)
	{
		uiClicked = bd.interpretMousePress(ma);
		if(!uiClicked)
		{
			initialPress = ma.getLocation();
			dragging = true;
		}
	}
	public void interpretMouseRelease(MouseAction ma)
	{
		if(!uiClicked)
		{
			Location start = initialPress;
			Location end = ma.getLocation();
			sge.getUserActionListener().nullMouseDragLocation();
			if(start.compareTo(end) != 0)
			{
				pselections.add(getPrismSelectionRegion(start, end));
			}
		}
		dragging = false;
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
	public void interpretMouseClick(MouseAction ma)
	{
		if(!uiClicked)
		{
			if(ma.isRightClick())
			{
				unSelect = true;
			}
			else
			{
				press = new Prism(ma.getLocation(), selectorSize, selectorSize, selectorSize);
			}
		}
	}
	public void performAIFunctions()
	{
		/*
		 * units that can build things, these units ar stored so the AI is able
		 * to quickly cycle through them to carry out the user's build commands
		 */
		LinkedList<Unit> builders = new LinkedList<Unit>();
		
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
			if(u.getBuildTree().size() > 0)
			{
				builders.add(u);
			}
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
		
		//builds units
		HashMap<String, LinkedList<Location>> buildLocations = bd.getBuildingLocations();
		Iterator<String> si = buildLocations.keySet().iterator();
		while(si.hasNext())
		{
			/*
			 * cycles through the units to be built, finds the first builder that
			 * is able to build each unit and orders it to do so regardless of the
			 * distance of that unit to its build order, if no builder is capable
			 * of building the unit then the build order simply remains in the hash
			 * map
			 */
			String name = si.next(); //the name of the building to be built
			
			if(buildLocations.get(name).size() > 0)
			{
				Iterator<Location> lli = buildLocations.get(name).iterator(); //the buildings to be built
				while(lli.hasNext())
				{
					Location l = lli.next(); //where the building is to be built
					boolean built = false;
					i = builders.iterator();
					while(i.hasNext() && !built)
					{
						Unit u = i.next();
						if(u.canBuild(name))
						{
							built = buildAt(name, u, l);
							if(built)
							{
								lli.remove();
							}
						}
					}
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
