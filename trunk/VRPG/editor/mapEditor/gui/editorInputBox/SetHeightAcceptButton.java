package editor.mapEditor.gui.editorInputBox;

import ui.button.*;
import ui.inputBox.*;
import controller.MapEditorController;

public class SetHeightAcceptButton extends Button
{
	public SetHeightAcceptButton(InputBox ib)
	{
		super("Accept", ib);
	}
	public void performAction(MapEditorController c)
	{
		String s = getInputBoxOwner();
		try
		{
			String sheight = c.getUICheckEngine().getInputBoxCheckEngine().getInputBox(
					s).getText();
			int height = Integer.parseInt(sheight);
			c.setMapHeight(height);
		}
		catch(NumberFormatException e){}
	}
}
