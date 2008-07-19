package ui.button.editButtons.mapEditorV1Buttons.mainEditMenuButtons;

import ui.button.*;
import world.World;

public class EditTerrainButton extends Button
{
	public EditTerrainButton(int x, int y, int width, int height)
	{
		super("Edit Terrain", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(2).setVisible(true);
	}
}
