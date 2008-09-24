package editor.mapEditor.gui.editorButton.chooserButton;

import ui.button.*;
import ui.menu.*;
import controller.MapEditorController;

public class PlaceFloorButton extends Button
{
	public PlaceFloorButton(Menu m)
	{
		super("Floor", m);
	}
	public void performAction(MapEditorController c)
	{
		c.setEditType(3);
	}
}