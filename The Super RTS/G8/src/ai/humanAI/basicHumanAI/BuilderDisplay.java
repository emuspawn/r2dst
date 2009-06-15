package ai.humanAI.basicHumanAI;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.*;
import graphics.GLCamera;
import javax.media.opengl.GL;
import com.sun.opengl.util.j2d.TextRenderer;
import sgEngine.EngineConstants;
import sgEngine.SGEngine;
import sgEngine.userAction.MouseAction;
import utilities.Location;
import world.owner.Neutral;
import world.unit.Unit;

/**
 * handles the building of units and buildings
 * @author Jack
 *
 */
public final class BuilderDisplay
{
	private boolean displayed = true;
	private TextRenderer tr;
	GLCamera c;
	boolean firstRun = true;
	Location clickLocation; //where the user last clicked on the UI
	SGEngine sge;
	
	private HashSet<String> unitNames = new HashSet<String>();
	HashMap<String, Rectangle> unitBounds = new HashMap<String, Rectangle>(); //the bounds of the unit buttons
	private HashSet<String> buildingNames = new HashSet<String>();
	HashMap<String, Rectangle> buildingBounds = new HashMap<String, Rectangle>(); //the bounds of the building buttons
	
	boolean buildingSelected = false;
	Unit building;
	
	double width;
	double height;
	
	/**
	 * stores the places where buildings are to be built, key=name of building
	 * value=location of building, the human ai automatically assigns a worker
	 * to go and build the building
	 */
	HashMap<String, LinkedList<Location>> buildingLocations = new HashMap<String, LinkedList<Location>>();
	/**
	 * stores the units that are to be built, key=name of the unit
	 * value=the number of the unit to be built
	 */
	HashMap<String, Integer> units = new HashMap<String, Integer>();
	
	public BuilderDisplay(GLCamera c, SGEngine sge)
	{
		this.c = c;
		this.sge = sge;
		
		Font font = new Font("SansSerif", Font.BOLD, 12);
		tr = new TextRenderer(font, true, true);
		
		Set<String> s = EngineConstants.unitFactory.getUnitNames();
		Iterator<String> si = s.iterator();
		while(si.hasNext())
		{
			String name = si.next();
			Unit u = EngineConstants.unitFactory.makeUnit(name, null, null);
			if(u.getMovement() == 0)
			{
				buildingNames.add(name);
			}
			else
			{
				unitNames.add(name);
			}
		}
		
		si = unitNames.iterator();
		while(si.hasNext())
		{
			units.put(si.next(), 0);
		}
	}
	/**
	 * draws the building display, lists all the buildings names, sets
	 * up the bounds of the building button rectangles when first run
	 * @param gl
	 * @return returns the height at which the building display is
	 * drawn (the bottom)
	 */
	private int drawBuildingDisplay(GL gl)
	{
		if(firstRun)
		{
			this.width = c.getWidth();
			this.height = c.getHeight();
		}
		if(width != c.getWidth() || height != c.getHeight())
		{
			firstRun = true;
		}
		
		
		int textSpacing = 16;
		int width = 180;
		int height = (buildingNames.size()+1)*textSpacing;
		Location l = new Location(0+10, c.getHeight()-height-10);
		
		gl.glBegin(GL.GL_QUADS);
		if(clickLocation != null)
		{
			gl.glColor4d(0, .5, 1, 1);
			//gl.glDisable(GL.GL_BLEND);
			double hcs = 3; //half click size
			gl.glVertex3d(clickLocation.x-hcs, clickLocation.y, .1);
			gl.glVertex3d(clickLocation.x, clickLocation.y+hcs, .1);
			gl.glVertex3d(clickLocation.x+hcs, clickLocation.y, .1);
			gl.glVertex3d(clickLocation.x, clickLocation.y-hcs, .1);
			//clickLocation = null;
		}
		gl.glColor4d(1, 0, 0, .3);
		gl.glVertex2d(l.x, l.y);
		gl.glVertex2d(l.x+width, l.y);
		gl.glVertex2d(l.x+width, l.y+height);
		gl.glVertex2d(l.x, l.y+height);
		gl.glEnd();
		
		Location start = new Location(l.x, l.y+height-16);
		if(!firstRun)
		{
			writeNames("-- construct buildings --", start, buildingNames, 3, 3, textSpacing, height);
		}
		else
		{
			buildingBounds = writeNames("-- construct buildings --", start, buildingNames, 3, 3, textSpacing, height);
		}
		
		return height;
	}
	/**
	 * draws the unit display, lists all the units names, lists the number of that
	 * kind of unit that are currently being built
	 * @param gl
	 */
	private void drawUnitDisplay(GL gl, int buildingDisplayHeight)
	{
		int textSpacing = 16;
		int width = 180;
		int height = (unitNames.size()+1)*textSpacing;
		Location l = new Location(0+10, c.getHeight()-buildingDisplayHeight-height-20);
		
		gl.glBegin(GL.GL_QUADS);
		gl.glColor4d(1, 0, 0, .3);
		gl.glVertex2d(l.x, l.y);
		gl.glVertex2d(l.x+width, l.y);
		gl.glVertex2d(l.x+width, l.y+height);
		gl.glVertex2d(l.x, l.y+height);
		gl.glEnd();
		
		Location start = new Location(l.x, l.y+height-16);
		if(!firstRun)
		{
			writeNames("-- construct units --", start, unitNames, 3, 3, textSpacing, width);
		}
		else
		{
			unitBounds = writeNames("-- construct units --", start, unitNames, 3, 3, textSpacing, width);
		}
		
		ArrayList<String> counts = new ArrayList<String>();
		Iterator<String> i = units.keySet().iterator();
		while(i.hasNext())
		{
			counts.add(""+units.get(i.next()));
		}
		writeNames("", start, counts, width-16, 3, textSpacing, 0);
	}
	/**
	 * writes the names of all the elements in the collection
	 * at the specified starting location on the screen, in addition
	 * it creates a list of rectangles that represent the bounds of
	 * the names written to the screen
	 * @param start the upper left coordinate of the names that are to
	 * be written to the screen
	 * @param c the collection to be written
	 * @param xoff the xoffset from the starting position
	 * @param yoff the yoffset from the starting position
	 * @param textSpacing the spacing between each line
	 * @param width the width of the bounds around the names
	 * @return returns a hash map representing the rectangles that
	 * outline the names drawn to the screen if the method was called
	 * for the first time, key=name value=rectangle bounds of that name,
	 * null otherwise
	 */
	private HashMap<String, Rectangle> writeNames(String title, Location start, Collection<String> c, int xoff, int yoff, int textSpacing, int width)
	{
		HashMap<String, Rectangle> b = new HashMap<String, Rectangle>();
		yoff = drawText(title, start, xoff, yoff, textSpacing);
		Iterator<String> si = c.iterator();
		while(si.hasNext())
		{
			String name = si.next();
			if(firstRun)
			{
				b.put(name, new Rectangle((int)start.x, (int)(start.y+yoff), width, textSpacing));
			}
			yoff = drawText(name, start, xoff, yoff, textSpacing);
		}
		return b;
	}
	public void drawBuilderDisplay(GL gl)
	{
		if(displayed && !buildingSelected)
		{
			gl.glPushMatrix(); //pushes the model view
			
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glLoadIdentity();
			
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glPushMatrix();
			gl.glLoadIdentity();
			gl.glOrtho(0, c.getWidth(), 0, c.getHeight(), 1, -1);
			
			int height = drawBuildingDisplay(gl);
			drawUnitDisplay(gl, height);
			
			gl.glPopMatrix();
			
			
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glPopMatrix();
			
			firstRun = false;
		}
		if(buildingSelected)
		{
			drawSelectedBuilding(gl);
		}
	}
	/**
	 * draws the currently selected building for placement purposes at
	 * the location of the mouse
	 * @param gl
	 */
	private void drawSelectedBuilding(GL gl)
	{
		Location mc = sge.getUserActionListener().getMouseLocation();
		Location l = c.getMapLocation(mc, 0);
		l = new Location(l.x, building.getRestingHeight(), l.z);
		building.setLocation(l);
		building.drawElement(gl);
	}
	/**
	 * draws text to the screen
	 * @param s the string to be drawn to the screen
	 * @param start the starting location for the string (before offsets)
	 * @param xoff the x offset (from the starting location)
	 * @param yoff the y offset (from the starting location)
	 * @param the spacing between each line of text
	 * @return returns the y offset for the next string (calculated from yoff+textSpacing)
	 */
	private int drawText(String s, Location start, int xoff, int yoff, int textSpacing)
	{
		tr.beginRendering((int)c.getWidth(), (int)c.getHeight());
		tr.draw(s, (int)(start.x+xoff), (int)(start.y+yoff));
		tr.endRendering();
		return yoff-textSpacing;
	}
	public void setDisplay(boolean setter)
	{
		displayed = setter;
	}
	public boolean isDisplayed()
	{
		return displayed;
	}
	/**
	 * checks to see if any part of the UI was clicked
	 * @param mc the location of the mouse click on screen
	 * @return returns true if a part of UI was clicked,
	 * false otherwise
	 */
	public boolean interpretMousePress(MouseAction ma)
	{
		//System.out.println("mouse clicked");
		Location mc = ma.getScreenLocation();
		if(!buildingSelected)
		{
			if(!ma.isRightClick())
			{
				mc.y = c.getHeight()-mc.y;
				
				Iterator<String> si = buildingBounds.keySet().iterator();
				while(si.hasNext() && !buildingSelected)
				{
					String name = si.next();
					if(buildingBounds.get(name).contains(mc.x, mc.y))
					{
						//System.out.println(name+" clicked");
						clickLocation = new Location(mc.x, mc.y);
						building = EngineConstants.unitFactory.makeUnit(name, new Neutral(), null);
						buildingSelected = true;
						return true;
					}
				}
				
				boolean unitSelected = false;
				si = unitBounds.keySet().iterator();
				while(si.hasNext() && !unitSelected)
				{
					String name = si.next();
					if(unitBounds.get(name).contains(mc.x, mc.y))
					{
						if(!ma.getMouseEvent().isControlDown())
						{
							units.put(name, units.get(name)+1);
						}
						else
						{
							units.put(name, units.get(name)+10);
						}
						return true;
					}
				}
			}
			return false;
		}
		else
		{
			if(!ma.isRightClick())
			{
				/*
				 * stores the building that is currently selected and the location of the mouse click in
				 * game space as a building location, this counts as if the UI was clicked
				 */
				Location l = c.getMapLocation(mc, 0);
				l = new Location(l.x, building.getRestingHeight(), l.z);
				if(buildingLocations.get(building.getName()) != null)
				{
					buildingLocations.get(building.getName()).add(l);
				}
				else
				{
					LinkedList<Location> ll = new LinkedList<Location>();
					ll.add(l);
					buildingLocations.put(building.getName(), ll);
				}
			}
			else
			{
				buildingSelected = false;
			}
			return true;
		}
	}
	/**
	 * gets the locations of where the user has chosen to construct buildings
	 * @return returns the hash map of user building locations
	 */
	public HashMap<String, LinkedList<Location>> getBuildingLocations()
	{
		return buildingLocations;
	}
	/**
	 * gets the hash map representing all the units and the number of each unit
	 * to be built
	 * @return
	 */
	public HashMap<String, Integer> getUnitConstructionCounts()
	{
		return units;
	}
}
