package world.action.actions;

import world.action.Action;

/**
 * the base action, whenever an element is doing no other actions it is idle
 * @author Jack
 *
 */
public class Idle extends Action
{
	public Idle()
	{
		super("idle");
	}
	public boolean performAction()
	{
		return false;
	}
}
