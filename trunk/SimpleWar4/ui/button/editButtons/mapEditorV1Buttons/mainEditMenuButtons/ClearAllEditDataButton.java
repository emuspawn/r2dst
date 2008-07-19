package ui.button.editButtons.mapEditorV1Buttons.mainEditMenuButtons;

import ui.button.*;
import world.World;

public class ClearAllEditDataButton extends Button
{
	public ClearAllEditDataButton(int x, int y, int width, int height)
	{
		super("Clear All Edit Data", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(4).setLocation(w.getMenuCheckEngine().getMenu(1).getRightSidePoint());
		w.getMenuCheckEngine().getMenu(4).setVisible(true);
	}
}
