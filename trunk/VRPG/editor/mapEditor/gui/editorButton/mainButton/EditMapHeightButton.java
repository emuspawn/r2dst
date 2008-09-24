package editor.mapEditor.gui.editorButton.mainButton;

import ui.button.*;
import ui.inputBox.InputBox;
import ui.menu.*;
import controller.MapEditorController;

public class EditMapHeightButton extends Button
{
	public EditMapHeightButton(Menu m)
	{
		super("Edit Map Height", m);
	}
	public void performAction(MapEditorController c)
	{
		InputBox ib = c.getUICheckEngine().getInputBoxCheckEngine().getInputBox("set height");
		ib.setLocation(c.getUICheckEngine().getMenuCheckEngine().getMenuGroup("main").getMenu(1).getRightSidePoint());
		ib.setVisible(true);
	}
}
