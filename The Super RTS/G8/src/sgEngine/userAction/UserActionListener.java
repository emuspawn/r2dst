package sgEngine.userAction;

import graphics.GLCamera;

import java.awt.event.*;

import sgEngine.SGEngine;
import utilities.Location;
import world.owner.Owner;

/**
 * provides the listener methods to queue up the user actions in the SGEngine
 * @author Jack
 *
 */
public class UserActionListener implements MouseListener, KeyListener, MouseMotionListener
{
	SGEngine sge;
	Owner owner;
	GLCamera c;
	boolean networked;
	Location mouseLocation;
	
	Location mousePress; //for determining if a mouse was clicked or not
	
	/**
	 * creates a new user action listener
	 * @param sge the SGEngine this listener is linked to
	 * @param owner the owner that is initiating the commands
	 * @param networked true if the game is networked (sets all
	 * commands to queue instead of runnin instantly to maintain
	 * synced SGEngines
	 */
	public UserActionListener(SGEngine sge, Owner owner, GLCamera c, boolean networked)
	{
		this.sge = sge;
		this.owner = owner;
		this.c = c;
		this.networked = networked;
	}
	/**
	 * sets the owner of this user action listener, all user actions generated
	 * are attributed to this owner
	 * @param o the owner
	 */
	public void setOwner(Owner o)
	{
		owner = o;
	}
	public void keyPressed(KeyEvent e)
	{
		int runTime = UserAction.RUN_INSTANTLY;
		if(networked)
		{
			runTime = sge.getIterationCount()+UserAction.advanceAmount;
		}
		sge.queueUserAction(new KeyPress(e.getKeyChar(), owner, c, runTime));
	}
	public void keyReleased(KeyEvent e)
	{
		int runTime = UserAction.RUN_INSTANTLY;
		if(networked)
		{
			runTime = sge.getIterationCount()+UserAction.advanceAmount;
		}
		sge.queueUserAction(new KeyRelease(e.getKeyChar(), owner, c, runTime));
	}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent e)
	{
		mousePress = c.getMapLocation(new Location(e.getPoint()), 1);
		queueMouseAction(e, MouseAction.press);
	}
	public void mouseReleased(MouseEvent e)
	{
		queueMouseAction(e, MouseAction.release);
	}
	public void mouseClicked(MouseEvent e)
	{
		Location mouseRelease = c.getMapLocation(new Location(e.getPoint()), 1);
		if(mouseRelease.compareTo(mousePress) == 0)
		{
			queueMouseAction(e, MouseAction.click);
		}
	}
	public void keyTyped(KeyEvent arg0){}
	private void queueMouseAction(MouseEvent e, byte type)
	{
		Location l = new Location(e.getPoint());
		Location mapLocation = c.getMapLocation(l, 1);
		
		boolean rightClick = e.getButton()==MouseEvent.BUTTON3;
		int runTime = UserAction.RUN_INSTANTLY;
		if(networked)
		{
			runTime = sge.getIterationCount()+UserAction.advanceAmount;
		}
		sge.queueUserAction(new MouseAction(mapLocation, owner, rightClick, type, runTime));
	}
	public void mouseDragged(MouseEvent arg0){}
	public void mouseMoved(MouseEvent e)
	{
		mouseLocation = new Location(e.getPoint());
	}
	/**
	 * gets where the mouse is on screen
	 * @return returns where the mouse is on screen
	 */
	public Location getMouseLocation()
	{
		return mouseLocation;
	}
}
