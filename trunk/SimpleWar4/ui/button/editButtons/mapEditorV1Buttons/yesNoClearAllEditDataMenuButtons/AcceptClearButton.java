package ui.button.editButtons.mapEditorV1Buttons.yesNoClearAllEditDataMenuButtons;

import ui.button.*;
import world.World;

public class AcceptClearButton extends Button
{
	public AcceptClearButton(int x, int y, int width, int height)
	{
		super("Accept", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getEditor().clearAllEditData();
		w.getMenuCheckEngine().getMenu(4).setVisible(false);
	}
}
