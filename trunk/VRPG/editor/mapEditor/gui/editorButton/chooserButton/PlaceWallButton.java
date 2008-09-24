package editor.mapEditor.gui.editorButton.chooserButton;

import ui.button.*;
import ui.menu.*;
import controller.MapEditorController;

public class PlaceWallButton extends Button
{
	public PlaceWallButton(Menu m)
	{
		super("Wall", m);
	}
	public void performAction(MapEditorController c)
	{
		c.setEditType(1);
	}
}