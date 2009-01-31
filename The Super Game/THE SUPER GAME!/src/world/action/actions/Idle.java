package world.action.actions;

import java.io.Serializable;

import world.action.Action;

/*
 * the unit is not doing anything
 */

public class Idle extends Action implements Serializable
{
	private static final long serialVersionUID = 1L;
	public Idle()
	{
		super("idle");
	}
	public void performAction(){}
}
