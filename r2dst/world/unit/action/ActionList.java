package world.unit.action;

import java.util.ArrayList;

/*
 * defines a list of actions that a unit follows, sometimes actions should be performed only if
 * certain criteria have been met, a check is used in this case, a unit's action is set to idle
 * if the pass the check continuing the list or failed if it failed the check stopping the list
 */

public class ActionList extends Action
{
	private ArrayList<Action> a = new ArrayList<Action>();
	private int index;
	
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
			//System.out.println(name+" performed action, "+a.get(index).getName());
			done = a.get(index).performAction();
			if(done)
			{
				index++;
				//System.out.println(name+" advanced to next action, "+a.get(index).getName());
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			//System.out.println(name+" done!");
			return true;
		}
		return false;
	}
}
