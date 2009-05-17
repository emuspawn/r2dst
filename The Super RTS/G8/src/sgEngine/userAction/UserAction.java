package sgEngine.userAction;

import world.owner.Owner;

/**
 * represents a user action
 * @author Jack
 *
 */
public abstract class UserAction
{
	/**
	 * used to queue items to be run instantly in the SGEngine
	 */
	public static final int RUN_INSTANTLY = -1;
	/**
	 * how many iterations before the mouse click takes affect,
	 * only used if the game is networked
	 */
	public static final int advanceAmount = 5;
	
	String name;
	int runTime;
	Owner owner;
	
	public UserAction(String name, Owner owner, int runTime)
	{
		this.name = name;
		this.owner = owner;
		this.runTime = runTime;
	}
	/**
	 * gets the iteration that this user action is to be performed on
	 * @return returns the run time
	 */
	public int getRunTime()
	{
		return runTime;
	}
	public Owner getOwner()
	{
		return owner;
	}
	/**
	 * gets the name of the user action
	 * @return returns the name of the user action
	 */
	public String getName()
	{
		return name;
	}
}
