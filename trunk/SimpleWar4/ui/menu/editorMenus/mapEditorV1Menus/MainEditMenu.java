package ui.menu.editorMenus.mapEditorV1Menus;

import ui.menu.*;
import ui.button.*;
import ui.button.editButtons.mapEditorV1Buttons.mainEditMenuButtons.*;

public class MainEditMenu extends Menu
{
	public MainEditMenu(int id)
	{
		super("Main Edit Menu", 0, 0, 130, 20, id);
		
		int slot = 1;
		addButton(new EditTerrainButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new Separator(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new Separator(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new EditEditorDisplayOptionsButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new ClearAllEditDataButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new CloseMainEditMenuButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new Separator(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new ExitButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
	}
}
