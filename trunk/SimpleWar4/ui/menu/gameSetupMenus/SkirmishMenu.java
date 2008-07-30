package ui.menu.gameSetupMenus;

import ui.menu.*;
import ui.button.gameSetupButtons.skirmishMenuButtons.*;

/*
 * do not copy this menu as a template for other menus, it uses a less common width of 150 to accomodate
 * text, common width is 130
 */

public class SkirmishMenu extends Menu
{
	public SkirmishMenu(int id)
	{
		super("Skirmish Menu", 0, 0, 150, 20, id);
		
		int slot = 1;
		addButton(new NormalSkirmishButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new AISkirmishButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new CancelSkirmishButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
	}
}
