package world.action;

/**
 * what every element is doing, ex moving, gathering resources, etc.
 * @author Jack
 *
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
	/**
	 * performs the actions
	 * @return returns true if the action is completed
	 */
	public abstract boolean performAction();
	public String toString()
	{
		return name;
	}
}
