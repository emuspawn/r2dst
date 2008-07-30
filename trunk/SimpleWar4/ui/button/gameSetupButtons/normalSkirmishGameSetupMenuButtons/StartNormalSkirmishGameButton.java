package ui.button.gameSetupButtons.normalSkirmishGameSetupMenuButtons;

import ui.button.*;
import world.World;

public class StartNormalSkirmishGameButton extends Button
{
	public StartNormalSkirmishGameButton(int x, int y, int width, int height)
	{
		super("Start Game!", x, y, width, height);
	}
	public void performAction(World w)
	{
		//w.setupGame();
		w.getRunSpecification().setGameStart(true);
		w.getRunSpecification().setMode(2);
		w.getMenuCheckEngine().setupMenuCheckEngine();
	}
}
