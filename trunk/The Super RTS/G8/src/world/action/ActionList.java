package world.action;

import java.util.ArrayList;

/**
 * defines a list of actions that a unit follows, sometimes actions should be performed only if
 * certain criteria have been met, a check is used in this case, a unit's action is set to idle
 * if the pass the check continuing the list or failed if it failed the check stopping the list
 */
public class ActionList extends Action
{
	private ArrayList<Action> a = new ArrayList<Action>();
	private int index; //the index of the action that the list is on
	
	public ActionList(String name)
	{
		super(name);
	}
	public void addActionToList(Action action)
	{
		a.add(action);
	}
	public boolean performAction()
	{
		boolean done = false;
		try
		{
			done = a.get(index).performAction();
			if(done)
			{
				index++;
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			return true;
		}
		return false;
	}
	/**
	 * cancels all the actions in the list
	 */
	public void cancelAction()
	{
		for(int i = a.size()-1; i >= 0; i--)
		{
			a.get(i).cancelAction();
		}
	}
}
