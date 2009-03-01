package editor.mapEditor;

import java.awt.*;
import javax.swing.*;
import utilities.Location;
import world.World;
import factory.*;
import graphics.Camera;
import java.awt.event.*;

/**
 * the canvas used by the map editor to draw the world and to
 * handel the mouse events of placing elements into the world
 * @author Jack
 *
 */

public class DrawCanvas extends JPanel implements MouseMotionListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	Camera c;
	MapEditor owner;
	World w;
	int gridSpacing = 15;
	JList list; //the terrain selection
	
	/**
	 * creates a new DrawCanvas
	 * @param owner the owning MapEditor
	 * @param c the camera to be used when determining visible elements to be displayed
	 * @param w the world that is displayed
	 */
	public DrawCanvas(MapEditor owner, Camera c, World w)
	{
		this.owner = owner;
		this.c = c;
		this.w = w;
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	private void drawStartLocations(Graphics2D g, Camera c)
	{
		int size = 20;
		for(int i = owner.startLocations.size()-1; i >= 0; i--)
		{
			Location l = owner.startLocations.get(i);
			if(c.isOnScreen(new Rectangle((int)l.x-size/2, (int)l.y-size/2, size, size)))
			{
				Point p = c.getScreenLocation(owner.startLocations.get(i));
				g.setColor(Color.red);
				g.fillOval(p.x-size/2, p.y-size/2, size, size);
				g.setColor(Color.black);
				g.drawString(""+i, p.x-3, p.y+4);
			}
		}
	}
	/*public void setTerrainSelectionList(JList l)
	{
		list = l;
	}*/
	/**
	 * draws the world, draws a grid (if option is turned on), draws start locatations,
	 * updates the width and height of the displayed region of the camera to the width
	 * and height of the JPanel
	 */
	public void paint(Graphics g)
	{
		c.setWidth(getWidth());
		c.setHeight(getHeight());
		Graphics2D g2 = (Graphics2D)g;
		w.drawWorld(g2, c);
		if(owner.drawGrid)
		{
			drawGrid(g2, c);
		}
		drawStartLocations(g2, c);
	}
	private void drawGrid(Graphics2D g, Camera c)
	{
		g.setColor(Color.black);
		for(int i = 0; i < w.getWidth(); i++)
		{
			if(i*gridSpacing-c.getyover() >= 0 && i*gridSpacing-c.getyover() <= getHeight() && i*gridSpacing >= 0 && i*gridSpacing <= w.getHeight())
			{
				g.drawLine(0, i*gridSpacing-c.getyover()-gridSpacing/2, getWidth(), i*gridSpacing-c.getyover()-gridSpacing/2);
			}
		}
		for(int i = 0; i < w.getHeight(); i++)
		{
			if(i*gridSpacing-c.getxover() >= 0 && i*gridSpacing-c.getxover() <= getWidth() && i*gridSpacing >= 0 && i*gridSpacing <= w.getWidth())
			{
				g.drawLine(i*gridSpacing-c.getxover()-gridSpacing/2, 0, i*gridSpacing-c.getxover()-gridSpacing/2, getHeight());
			}
		}
	}
	public void mouseDragged(MouseEvent e)
	{
		if(!owner.placingStartLocation)
		{
			String type = owner.getSelectionPanel().getDisplayedTabName();
			list = owner.getSelectionPanel().getDisplayedJList();
			if(e.getModifiers() == MouseEvent.BUTTON1_MASK)
			{
				if(list != null && list.getSelectedValue() != null)
				{
					int x = ((e.getPoint().x+c.getxover()+gridSpacing/2)/gridSpacing)*gridSpacing;
					int y = ((e.getPoint().y+c.getyover()+gridSpacing/2)/gridSpacing)*gridSpacing;
					if(w.getDynamicMap().getElement(new Point(x, y)) == null)
					{
						if(type.equalsIgnoreCase("terrain"))
						{
							w.registerElement(TerrainFactory.makeTerrain((String)list.getSelectedValue(), new Location(x, y), gridSpacing, gridSpacing));
						}
						else if(type.equalsIgnoreCase("resource"))
						{
							w.registerElement(ResourceFactory.makeResource((String)list.getSelectedValue(), new Location(x, y)));
						}
					}
				}
			}
			else if(e.getModifiers() == MouseEvent.BUTTON3_MASK)
			{
				int x = ((e.getPoint().x+c.getxover()+gridSpacing/2)/gridSpacing)*gridSpacing;
				int y = ((e.getPoint().y+c.getyover()+gridSpacing/2)/gridSpacing)*gridSpacing;
				w.getDynamicMap().removeElement(new Point(x, y));
			}
		}
	}
	public void mouseMoved(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e)
	{
		if(owner.placingStartLocation)
		{
			//System.out.println("mouse clicked");
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				Point p = e.getPoint();
				owner.startLocations.add(new Location(p.x+c.getxover(), p.y+c.getyover()));
			}
			owner.placingStartLocation = false;
		}
	}
}