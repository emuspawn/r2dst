package ui.menu.gameSetupMenus;

import ui.menu.*;
import ui.button.*;
import ui.button.gameSetupButtons.normalSkirmishGameSetupMenuButtons.*;

public class NormalSkirmishGameSetupMenu extends Menu
{
	public NormalSkirmishGameSetupMenu(int id)
	{
		super("Setup", 0, 0, 130, 20, id);
		
		int slot = 1;
		addButton(new SetPlayerAmountButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new SelectPlayerEditButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new Separator(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new StartNormalSkirmishGameButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
	}
}
