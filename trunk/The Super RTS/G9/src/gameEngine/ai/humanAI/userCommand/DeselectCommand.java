package gameEngine.ai.humanAI.userCommand;

import gameEngine.ai.AI;
import gameEngine.world.unit.Unit;

public class DeselectCommand extends UserCommand
{
	public void updateUnit(Unit u, AI ai, boolean override)
	{
		u.setSelected(false);
	}
}
