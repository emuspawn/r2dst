package ui.button.gameSetupButtons.gameSetupMenuButtons;

import ui.button.*;
import world.World;

public class SkirmishGameButton extends Button
{
	public SkirmishGameButton(int x, int y, int width, int height)
	{
		super("Skirmish Game", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(2).setLocation(w.getMenuCheckEngine().getMenu(1).getRightSidePoint());
		w.getMenuCheckEngine().getMenu(2).setVisible(true);
	}
}
