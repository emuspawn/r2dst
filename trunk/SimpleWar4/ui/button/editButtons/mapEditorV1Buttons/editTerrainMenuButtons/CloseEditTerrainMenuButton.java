package ui.button.editButtons.mapEditorV1Buttons.editTerrainMenuButtons;

import ui.button.*;
import world.World;

public class CloseEditTerrainMenuButton extends Button
{
	public CloseEditTerrainMenuButton(int x, int y, int width, int height)
	{
		super("Close Menu", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(2).setVisible(false);
	}
}
