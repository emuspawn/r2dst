package gameEngine.world.unit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

import javax.media.opengl.GL;
import utilities.Region;
import utilities.SpatialPartition;
import gameEngine.world.action.Action;
import gameEngine.world.owner.Owner;
import gameEngine.world.shot.ShotEngine;
import gameEngine.world.weapon.Weapon;

public abstract class Unit extends Region
{
	String name;
	Owner o;
	Weapon w;
	double movement; //pixels per second
	double life = 10;
	double maxLife;
	LinkedBlockingQueue<Action> a = new LinkedBlockingQueue<Action>();
	boolean selected = false;
	protected HashSet<String> buildTree = new HashSet<String>(); //names of buildable units
	boolean dead = false;
	
	/**
	 * creates a unit
	 * @param o
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Unit(String name, Owner o, double x, double y, double width, double height, double movement, Weapon w)
	{
		super(x, y, width, height);
		this.name = name;
		this.o = o;
		this.w = w;
		this.movement = movement;
	}
	public HashSet<String> getBuildTree()
	{
		return buildTree;
	}
	public void setLife(double setter)
	{
		life = setter;
	}
	public double getLife()
	{
		return life;
	}
	public Action getCurrentAction()
	{
		if(a.size() > 0)
		{
			return a.peek();
		}
		return null;
	}
	public Owner getOwner()
	{
		return o;
	}
	/**
	 * cancels all action currently queued for the given unit
	 */
	public void clearActions()
	{
		while(a.size() > 0)
		{
			a.remove().cancelAction();
		}
	}
	public void addAction(Action action)
	{
		a.add(action);
	}
	public boolean isDead()
	{
		return dead;
	}
	public void setDead()
	{
		dead = true;
	}
	/**
	 * updates the unit, performs its current action, fires the weapon
	 * @param units the spatial partitions representing all game units
	 * @param se the shot engine, used to register shots fired from the weapon
	 */
	public void updateUnit(HashMap<Owner, SpatialPartition> units, ShotEngine se, double tdiff)
	{
		if(life <= 0)
		{
			dead = true;
		}
		else
		{
			if(w != null)
			{
				w.updateWeapon(tdiff);
				w.fireWeapon(o, x, y, units, se);
			}
			if(a.size() > 0)
			{
				boolean done = a.peek().performAction(tdiff);
				if(done)
				{
					a.remove();
				}
			}
		}
	}
	protected void drawSelection(GL gl)
	{
		double d = 0.01; //depth
		gl.glPushMatrix();
		double w = .26; //how much of each side the highlight box takes up
		gl.glLineWidth(2);
		gl.glColor4d(.9882, .9137, .0118, 1);
		//bottom left
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3d(x+width*w, y, d);
		gl.glVertex3d(x, y, d);
		gl.glVertex3d(x, y+height*w, d);
		gl.glEnd();
		//bottom right
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3d(x+width*(1-w), y, d); //x+width-width*w = x+width*(1-w)
		gl.glVertex3d(x+width, y, d);
		gl.glVertex3d(x+width, y+height*w, d);
		gl.glEnd();
		//top right
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3d(x+width*(1-w), y+height, d); //x+width-width*w = x+width*(1-w)
		gl.glVertex3d(x+width, y+height, d);
		gl.glVertex3d(x+width, y+height*(1-w), d); //y+height-height*w = y+height*(1-w)
		gl.glEnd();
		//top left
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3d(x+width*w, y+height, d);
		gl.glVertex3d(x, y+height, d);
		gl.glVertex3d(x, y+height*(1-w), d); //y+height-height*w = y+height*(1-w)
		gl.glEnd();
		gl.glPopMatrix();
	}
	public void drawUnit(GL gl)
	{
		
		double d = 0; //depth
		double[] c = o.getColor();
		gl.glColor3d(c[0], c[1], c[2]);
		gl.glBegin(GL.GL_POLYGON);
		gl.glVertex3d(x+width*.2, y, d);
		gl.glVertex3d(x+width*.8, y, d);
		gl.glVertex3d(x+width, y+height, d);
		gl.glVertex3d(x, y+height, d);
		gl.glEnd();
		
		if(a.size() > 0)
		{
			a.peek().drawAction(gl);
		}
		
		if(selected)
		{
			drawSelection(gl);
		}
		
		/*gl.glLineWidth(1);
		gl.glColor3d(0, 0, 0);
		drawRegion(gl);*/
	}
	public boolean isSelected()
	{
		return selected;
	}
	public void setSelected(boolean setter)
	{
		selected = setter;
	}
	public double getMovement()
	{
		return movement;
	}
}
