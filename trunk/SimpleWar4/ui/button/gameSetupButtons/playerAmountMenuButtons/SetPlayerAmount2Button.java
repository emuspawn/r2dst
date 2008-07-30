package ui.button.gameSetupButtons.playerAmountMenuButtons;

import ui.button.*;
import world.World;

public class SetPlayerAmount2Button extends Button
{
	public SetPlayerAmount2Button(int x, int y, int width, int height)
	{
		super("Players 2", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getRunSpecification().getNormalSkirmishSpecification().setPlayerAmount(2);
		w.getMenuCheckEngine().getMenu(4).setVisible(false);
	}
}
