package mapEditor.gui.editorButton.mainButton;

import ui.button.*;
import ui.inputBox.InputBox;
import ui.menu.*;
import controller.MapEditorController;

public class EditMapNameButton extends Button
{
	public EditMapNameButton(Menu m)
	{
		super("Edit Map Name", m);
	}
	public void performAction(MapEditorController c)
	{
		InputBox ib = c.getUICheckEngine().getInputBoxCheckEngine().getInputBox("set map name");
		ib.setLocation(c.getUICheckEngine().getMenuCheckEngine().getMenuGroup("main").getMenu(1).getRightSidePoint());
		ib.setVisible(true);
	}
}
