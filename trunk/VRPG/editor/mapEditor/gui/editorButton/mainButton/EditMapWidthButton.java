package editor.mapEditor.gui.editorButton.mainButton;

import ui.button.*;
import ui.menu.*;
import controller.MapEditorController;
import ui.inputBox.*;

public class EditMapWidthButton extends Button
{
	public EditMapWidthButton(Menu m)
	{
		super("Edit Map Width", m);
	}
	public void performAction(MapEditorController c)
	{
		InputBox ib = c.getUICheckEngine().getInputBoxCheckEngine().getInputBox("set width");
		ib.setLocation(c.getUICheckEngine().getMenuCheckEngine().getMenuGroup("main").getMenu(1).getRightSidePoint());
		ib.setVisible(true);
	}
}
