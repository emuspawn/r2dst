package ui.menu.editorMenus.mapEditorV1Menus;

import ui.menu.*;
import ui.button.editButtons.mapEditorV1Buttons.yesNoClearAllEditDataMenuButtons.*;

public class YesNoClearAllEditDataMenu extends Menu
{
	public YesNoClearAllEditDataMenu(int id)
	{
		super("Decision?", 0, 0, 130, 20, id);
		
		int slot = 1;
		addButton(new AcceptClearButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new CancelClearButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
	}
}
