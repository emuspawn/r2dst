package ui.menu.gameMenus;

import ui.menu.*;
import ui.button.*;
import ui.button.editButtons.mapEditorV1Buttons.mainEditMenuButtons.*;

public class MainGameMenu extends Menu
{
	public MainGameMenu(int id)
	{
		super("Main Game Menu", 0, 0, 130, 20, id);
		
		int slot = 1;
		addButton(new Separator(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new ExitButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
	}
}
