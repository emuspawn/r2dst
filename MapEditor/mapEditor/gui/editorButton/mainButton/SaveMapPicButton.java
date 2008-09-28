package mapEditor.gui.editorButton.mainButton;

import ui.button.*;
import ui.menu.*;
import controller.MapEditorController;

public class SaveMapPicButton extends Button
{
	public SaveMapPicButton(Menu m)
	{
		super("Save Map (pic)", m);
	}
	public void performAction(MapEditorController c)
	{
		c.savePicNextIteration();
	}
}
