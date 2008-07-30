package ui.button.gameSetupButtons.gameSetupMenuButtons;

import ui.button.*;
import world.World;

public class NetworkGameButton extends Button
{
	public NetworkGameButton(int x, int y, int width, int height)
	{
		super("Network Game", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(2).setVisible(false);
	}
}
