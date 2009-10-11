package gameEngine.ai.humanAI.userCommand;

import gameEngine.ai.AI;
import gameEngine.world.unit.Unit;

/**
 * moves units
 * @author Jack
 *
 */
public class MoveCommand extends UserCommand
{
	double x;
	double y;
	
	public MoveCommand(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public void updateUnit(Unit u, AI ai, boolean override)
	{
		if(u.isSelected())
		{
			if(override)
			{
				u.clearActions();
			}
			ai.moveUnit(u, x, y);
		}
	}
}
