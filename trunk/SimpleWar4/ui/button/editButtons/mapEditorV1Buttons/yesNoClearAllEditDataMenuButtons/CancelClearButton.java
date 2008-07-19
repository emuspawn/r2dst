package ui.button.editButtons.mapEditorV1Buttons.yesNoClearAllEditDataMenuButtons;

import ui.button.*;
import world.World;

public class CancelClearButton extends Button
{
	public CancelClearButton(int x, int y, int width, int height)
	{
		super("Cancel", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getMenuCheckEngine().getMenu(4).setVisible(false);
	}
}
