package ui.button.gameSetupButtons.normalSkirmishGameSetupMenuButtons;

import ui.button.*;
import world.World;

public class SelectPlayerEditButton extends Button
{
	public SelectPlayerEditButton(int x, int y, int width, int height)
	{
		super("Edit Players", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(5).setLocation(w.getMenuCheckEngine().getMenu(3).getRightSidePoint());
		w.getMenuCheckEngine().getMenu(5).setVisible(true);
	}
}
