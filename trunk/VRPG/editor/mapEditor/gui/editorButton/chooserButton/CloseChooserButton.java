package editor.mapEditor.gui.editorButton.chooserButton;

import ui.button.*;
import ui.menu.*;
import controller.MapEditorController;

public class CloseChooserButton extends Button
{
	public CloseChooserButton(Menu m)
	{
		super("Close Menu", m);
	}
	public void performAction(MapEditorController c)
	{
		c.getUICheckEngine().getMenuCheckEngine().getMenuGroup("chooser").getMenu(1).setVisible(false);
	}
}
