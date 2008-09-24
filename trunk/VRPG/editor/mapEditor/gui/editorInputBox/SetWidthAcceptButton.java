package editor.mapEditor.gui.editorInputBox;

import ui.button.*;
import ui.inputBox.*;
import controller.MapEditorController;

public class SetWidthAcceptButton extends Button
{
	public SetWidthAcceptButton(InputBox ib)
	{
		super("Accept", ib);
	}
	public void performAction(MapEditorController c)
	{
		String s = getInputBoxOwner();
		try
		{
			String swidth = c.getUICheckEngine().getInputBoxCheckEngine().getInputBox(
					s).getText();
			int width = Integer.parseInt(swidth);
			c.setMapWidth(width);
		}
		catch(NumberFormatException e){}
	}
}
