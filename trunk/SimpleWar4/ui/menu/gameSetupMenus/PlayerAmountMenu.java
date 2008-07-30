package ui.menu.gameSetupMenus;

import ui.menu.*;
import ui.button.*;
import ui.button.gameSetupButtons.playerAmountMenuButtons.*;

public class PlayerAmountMenu extends Menu
{
	public PlayerAmountMenu(int id)
	{
		super("Player Amount", 0, 0, 130, 20, id);
		
		int slot = 1;
		addButton(new SetPlayerAmount2Button(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new SetPlayerAmount3Button(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new SetPlayerAmount4Button(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
	}
}
