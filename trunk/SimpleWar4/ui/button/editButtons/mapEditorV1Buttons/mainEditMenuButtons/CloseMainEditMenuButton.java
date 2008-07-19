package ui.button.editButtons.mapEditorV1Buttons.mainEditMenuButtons;

import ui.button.*;
import world.World;

public class CloseMainEditMenuButton extends Button
{
	public CloseMainEditMenuButton(int x, int y, int width, int height)
	{
		super("Close Menu", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(1).setVisible(false);
	}
}
