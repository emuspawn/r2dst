package ui.button.gameSetupButtons.skirmishMenuButtons;

import ui.button.*;
import world.World;

public class NormalSkirmishButton extends Button
{
	public NormalSkirmishButton(int x, int y, int width, int height)
	{
		super("Normal Skirmish Game", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(3).setLocation(w.getMenuCheckEngine().getMenu(2).getRightSidePoint());
		w.getMenuCheckEngine().getMenu(3).setVisible(true);
	}
}
