package editor.mapEditor.gui.editorButton.mainButton;

import ui.button.*;
import ui.menu.*;
import controller.MapEditorController;

public class ExitButton extends Button
{
	public ExitButton(Menu m)
	{
		super("Exit", m);
	}
	public void performAction(MapEditorController c)
	{
		System.exit(0);
	}
}
