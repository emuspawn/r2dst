package world.unit.action.actions;

import world.unit.action.*;

/*
 * does nothing, the unit is idle
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
