package mapEditor.gui.editorButton.chooserButton;

import ui.button.*;
import ui.menu.*;
import controller.MapEditorController;

public class PlaceDirtButton extends Button
{
	public PlaceDirtButton(Menu m)
	{
		super("Dirt", m);
	}
	public void performAction(MapEditorController c)
	{
		c.setEditType(4);
	}
}