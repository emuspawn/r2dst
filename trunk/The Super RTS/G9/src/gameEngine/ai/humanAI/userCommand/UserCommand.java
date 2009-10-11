package gameEngine.ai.humanAI.userCommand;

import gameEngine.ai.AI;
import gameEngine.world.unit.Unit;

/**
 * the command a user has issued
 * @author Jack
 *
 */
public abstract class UserCommand
{
	/**
	 * updates the unit based off the type of command issued
	 * @param u the unit that is receiving the command
	 * @param ai a reference to the underlying ai in order to
	 * carry out the issued command
	 * @param override if true then the issued user command is mean
	 * to replace all other actions the unit is currently doing
	 */
	public abstract void updateUnit(Unit u, AI ai, boolean override);
}
