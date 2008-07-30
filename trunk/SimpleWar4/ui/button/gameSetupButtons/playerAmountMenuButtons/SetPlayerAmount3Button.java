package ui.button.gameSetupButtons.playerAmountMenuButtons;

import ui.button.*;
import world.World;

public class SetPlayerAmount3Button extends Button
{
	public SetPlayerAmount3Button(int x, int y, int width, int height)
	{
		super("Players 3", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getRunSpecification().getNormalSkirmishSpecification().setPlayerAmount(3);
		w.getMenuCheckEngine().getMenu(4).setVisible(false);
	}
}
