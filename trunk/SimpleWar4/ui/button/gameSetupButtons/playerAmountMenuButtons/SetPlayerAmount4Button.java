package ui.button.gameSetupButtons.playerAmountMenuButtons;

import ui.button.*;
import world.World;

public class SetPlayerAmount4Button extends Button
{
	public SetPlayerAmount4Button(int x, int y, int width, int height)
	{
		super("Players 4", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getRunSpecification().getNormalSkirmishSpecification().setPlayerAmount(4);
		w.getMenuCheckEngine().getMenu(4).setVisible(false);
	}
}
