package ui.menu.gameSetupMenus;

import ui.menu.*;
import ui.button.*;
import ui.button.editButtons.mapEditorV1Buttons.mainEditMenuButtons.*;
import ui.button.gameSetupButtons.gameSetupMenuButtons.*;

public class GameSetupMenu extends Menu
{
	public GameSetupMenu(int id)
	{
		super("Main Menu", 0, 0, 130, 20, id);
		
		int slot = 1;
		addButton(new SkirmishGameButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new NetworkGameButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new EditorModeButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new Separator(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new ExitButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		
		setVisible(true);
	}
}
