package world.action;

/**
 * the action of every unit
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
	/**
	 * cancels the action, called whenever the action is changed from
	 * one thing to another
	 */
	public abstract void cancelAction();
	public String toString()
	{
		return name;
	}
}
