package ui.button.editButtons.mapEditorV1Buttons.editTerrainMenuButtons;

import ui.button.*;
import world.World;

public class DrawWaterButton extends Button
{
	public DrawWaterButton(int x, int y, int width, int height)
	{
		super("Draw Water", x, y, width, height);
	}
	public void performAction(World w)
	{
		w.getEditor().setEditType(1);
		w.getMenuCheckEngine().getMenu(3).setLocation(w.getMenuCheckEngine().getMenu(2).getRightSidePoint());
		w.getMenuCheckEngine().getMenu(3).setVisible(true);
	}
}
