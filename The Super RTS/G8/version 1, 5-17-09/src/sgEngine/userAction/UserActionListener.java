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
public class UserActionListener implements MouseListener, KeyListener
{
	SGEngine sge;
	Owner owner;
	GLCamera c;
	boolean networked;
	
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
	public void mouseClicked(MouseEvent e)
	{
		Location l = new Location(e.getPoint());
		l.y = c.getHeight()-l.y; //because openGL has (0, 0) as the bottom left point
		
		boolean rightClick = e.getButton()==MouseEvent.BUTTON3;
		int runTime = UserAction.RUN_INSTANTLY;
		if(networked)
		{
			runTime = sge.getIterationCount()+UserAction.advanceAmount;
		}
		sge.queueUserAction(new MouseClick(l, owner, rightClick, runTime));
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
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
	public void keyTyped(KeyEvent arg0){}
}
