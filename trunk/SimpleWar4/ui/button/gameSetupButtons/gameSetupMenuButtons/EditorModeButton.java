package ui.button.gameSetupButtons.gameSetupMenuButtons;

import ui.button.*;
import world.World;

public class EditorModeButton extends Button
{
	public EditorModeButton(int x, int y, int width, int height)
	{
		super("Editor Mode", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(2).setVisible(false);
	}
}
