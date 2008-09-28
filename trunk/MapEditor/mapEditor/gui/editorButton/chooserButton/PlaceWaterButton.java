package mapEditor.gui.editorButton.chooserButton;

import ui.button.*;
import ui.menu.*;
import controller.MapEditorController;

public class PlaceWaterButton extends Button
{
	public PlaceWaterButton(Menu m)
	{
		super("Water", m);
	}
	public void performAction(MapEditorController c)
	{
		c.setEditType(2);
	}
}