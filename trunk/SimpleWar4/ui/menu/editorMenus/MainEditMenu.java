package ui.menu.editorMenus;

import ui.menu.*;
import ui.button.*;
import ui.button.editButtons.mainEditMenuButtons.*;

public class MainEditMenu extends Menu
{
	public MainEditMenu(int id)
	{
		super("Main Edit Menu", 0, 0, 130, 20, id);
		
		setMovable(true);
		
		int slot = 1;
		addButton(new EditTerrainButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new Separator(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new Separator(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new EditEditorDisplayOptions(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new Separator(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new ExitButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
	}
}
