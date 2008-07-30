package ui.button.gameSetupButtons.skirmishMenuButtons;

import ui.button.*;
import world.World;

public class CancelSkirmishButton extends Button
{
	public CancelSkirmishButton(int x, int y, int width, int height)
	{
		super("Cancel", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(2).setVisible(false);
		w.getMenuCheckEngine().getMenu(3).setVisible(false);
		w.getMenuCheckEngine().getMenu(4).setVisible(false);
		w.getMenuCheckEngine().getMenu(5).setVisible(false);
	}
}
