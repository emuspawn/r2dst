package ui.button.gameSetupButtons.normalSkirmishGameSetupMenuButtons;

import ui.button.*;
import world.World;

public class SetPlayerAmountButton extends Button
{
	public SetPlayerAmountButton(int x, int y, int width, int height)
	{
		super("Set Number of Players", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(4).setLocation(w.getMenuCheckEngine().getMenu(3).getRightSidePoint());
		w.getMenuCheckEngine().getMenu(4).setVisible(true);
	}
}
