package mapEditor.gui.editorButton.mainButton;

import ui.button.*;
import ui.menu.*;
import controller.MapEditorController;

public class SaveMapButton extends Button
{
	public SaveMapButton(Menu m)
	{
		super("Save Map (.txt)", m);
	}
	public void performAction(MapEditorController c)
	{
		c.saveNextIteration();
	}
}
