package ui.menu.gameSetupMenus;

import ui.menu.*;
import ui.button.gameSetupButtons.selectPlayerEditMenuButtons.*;

public class SelectPlayerEditMenu extends Menu
{
	public SelectPlayerEditMenu(int id)
	{
		super("Select Player", 0, 0, 130, 20, id);
		
		int slot = 1;
		addButton(new SelectPlayer1Button(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
	}
}
