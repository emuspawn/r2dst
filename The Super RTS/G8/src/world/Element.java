package world;

import javax.media.opengl.GL;
import utilities.Location;
import utilities.Prism;
import world.action.Action;
import world.action.actions.Idle;
import world.owner.Owner;

public abstract class Element extends Prism
{
	private String name;
	private Owner owner;
	private Action a = new Idle();
	private double life;
	boolean dead = false;
	
	public Element(String name, Location l, Owner owner, double life, double width, double height, double depth)
	{
		super(l, width, height, depth);
		this.owner = owner;
		this.name = name;
		this.life = life;
	}
	public Owner getOwner()
	{
		return owner;
	}
	public boolean isDead()
	{
		return dead;
	}
	public void setDead()
	{
		dead = true;
	}
	public String getName()
	{
		return name;
	}
	public double getLife()
	{
		return life;
	}
	public void setLife(double setter)
	{
		life = setter;
	}
	public Action getAction()
	{
		return a;
	}
	/**
	 * cancels the old action of the element, sets the
	 * current action to the passed action
	 * @param action the new action of the element
	 * @param completed true if they completed their
	 * last action, false if it was cancelled
	 */
	public void setAction(Action action, boolean completed)
	{
		if(!completed)
		{
			a.cancelAction();
		}
		a = action;
	}
	public abstract void drawElement(GL gl);
}
