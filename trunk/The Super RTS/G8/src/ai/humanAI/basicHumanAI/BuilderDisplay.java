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
	private HashSet<String> buildingNames = new HashSet<String>();
	HashMap<String, Rectangle> buildingBounds = new HashMap<String, Rectangle>(); //the bounds of the building buttons
	
	boolean buildingSelected = false;
	Unit building;
	
	/**
	 * stores the places where buildings are to be built, key=name of building
	 * value=location of building, the human ai automatically assigns a worker
	 * to go and build the building
	 */
	HashMap<String, LinkedList<Location>> buildingLocations = new HashMap<String, LinkedList<Location>>();
	
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
	}
	public void drawBuilderDisplay(GL gl)
	{
		if(displayed && !buildingSelected)
		{

			int width = 180;
			int height = (buildingNames.size()+1)*16;
			Location l = new Location(0+10, c.getHeight()-height-10);
			
			gl.glPushMatrix(); //pushes the model view
			
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glLoadIdentity();
			
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glPushMatrix();
			gl.glLoadIdentity();
			gl.glOrtho(0, c.getWidth(), 0, c.getHeight(), 1, -1);
			
			
			gl.glBegin(GL.GL_QUADS);
			
			if(clickLocation != null)
			{
				gl.glColor4d(0, .5, 1, 1);
				//gl.glDisable(GL.GL_BLEND);
				double hcs = 3; //half click size
				gl.glVertex3d(clickLocation.x-hcs, clickLocation.y, .9);
				gl.glVertex3d(clickLocation.x, clickLocation.y+hcs, .9);
				gl.glVertex3d(clickLocation.x+hcs, clickLocation.y, .9);
				gl.glVertex3d(clickLocation.x, clickLocation.y-hcs, .9);
				//clickLocation = null;
			}

			gl.glColor4d(1, 0, 0, .3);
			gl.glVertex2d(l.x, l.y);
			gl.glVertex2d(l.x+width, l.y);
			gl.glVertex2d(l.x+width, l.y+height);
			gl.glVertex2d(l.x, l.y+height);
			
			
			
			gl.glEnd();
			
			int yoff = 3;
			int xoff = 3;
			int textSpacing = 16;
			Location start = new Location(l.x, l.y+height-16);
			yoff = drawText("-- Construct Buildings --", start, xoff, yoff, textSpacing);
			Iterator<String> si = buildingNames.iterator();
			while(si.hasNext())
			{
				String name = si.next();
				if(firstRun)
				{
					buildingBounds.put(name, new Rectangle((int)start.x, (int)(start.y+yoff), width, textSpacing));
					/*System.out.println("yoff = "+yoff);
					System.out.println("start = "+start);
					System.out.println("bb created: "+(int)start.x+", "+(int)(start.y-yoff)+", "+width+", "+textSpacing);
					System.out.println("-----------");*/
				}
				yoff = drawText(name, start, xoff, yoff, textSpacing);
			}
			
			
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
		Location mc = ma.getScreenLocation();
		if(!buildingSelected)
		{
			mc.y = c.getHeight()-mc.y;
			Iterator<String> si = buildingBounds.keySet().iterator();
			while(si.hasNext() && !buildingSelected)
			{
				String name = si.next();
				if(buildingBounds.get(name).contains(mc.x, mc.y))
				{
					System.out.println(name+" clicked");
					clickLocation = new Location(mc.x, mc.y);
					building = EngineConstants.unitFactory.makeUnit(name, new Neutral(), null);
					buildingSelected = true;
					return true;
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
				removeEmptyBuildBuckets();
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
	 * cycles through the build location hash map and removes names that
	 * are associated with a linked list of size 0
	 */
	private void removeEmptyBuildBuckets()
	{
		Iterator<String> si = buildingLocations.keySet().iterator();
		while(si.hasNext())
		{
			String key = si.next();
			if(buildingLocations.get(key).size() == 0)
			{
				buildingLocations.remove(key);
			}
		}
	}
}
