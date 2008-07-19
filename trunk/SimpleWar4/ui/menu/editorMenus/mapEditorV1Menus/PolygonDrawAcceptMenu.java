package ui.menu.editorMenus.mapEditorV1Menus;

import ui.menu.*;
import ui.button.editButtons.mapEditorV1Buttons.polygonDrawAcceptMenuButtons.*;

public class PolygonDrawAcceptMenu extends Menu
{
	public PolygonDrawAcceptMenu(int id)
	{
		super("Done Drawing?", 130, 0, 130, 20, id);
		
		setMovable(true);
		
		int slot = 1;
		addButton(new AcceptPolygonButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
		addButton(new CancelPolygonButton(x, (y+slotHeight)*slot, slotWidth, slotHeight));
		slot++;
	}
}
