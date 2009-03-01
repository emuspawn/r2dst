package world.action;

/*
 * what every element is doing, ex moving, gathering resources, etc.
 */

public abstract class Action
{
	String name;
	public Action(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public abstract boolean performAction();
	public String toString()
	{
		return name;
	}
}
