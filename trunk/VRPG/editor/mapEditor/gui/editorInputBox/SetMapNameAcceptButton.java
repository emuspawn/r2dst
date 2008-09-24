package editor.mapEditor.gui.editorInputBox;

import ui.button.*;
import ui.inputBox.*;
import controller.MapEditorController;

public class SetMapNameAcceptButton extends Button
{
	public SetMapNameAcceptButton(InputBox ib)
	{
		super("Accept", ib);
	}
	public void performAction(MapEditorController c)
	{
		String s = getInputBoxOwner();
		String name = c.getUICheckEngine().getInputBoxCheckEngine().getInputBox(
				s).getText();
		c.setMapName(name);
	}
}
