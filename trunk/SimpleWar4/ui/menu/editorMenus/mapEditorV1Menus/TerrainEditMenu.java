package ui.menu.editorMenus.mapEditorV1Menus;

import ui.menu.*;
import ui.button.*;
import ui.button.editButtons.mapEditorV1Buttons.editTerrainMenuButtons.*;

public class TerrainEditMenu extends Menu
{
	public TerrainEditMenu(int id)
	{
		super("Terrain Edit Menu", 130, 0, 130, 20, id);
		
		setMovable(true);
		
		int slot = 1;
		addButton(new DrawWaterButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new Separator(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new CloseEditTerrainMenuButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
	}
}
