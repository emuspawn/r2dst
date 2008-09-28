package mapEditor.gui;

import ui.inputBox.*;
import ui.button.*;
import mapEditor.gui.editorInputBox.*;

public class BoxRegister implements InputBoxRegister
{
	public BoxRegister(){}
	public void registerInputBoxes(InputBoxCheckEngine ibce)
	{
		//set width box
		InputBox swb = new InputBox("set width", 0, 0);
		swb.addButton(new SetWidthAcceptButton(swb));
		swb.addButton(new CloseInputBoxButton(swb));
		ibce.registerInputBox(swb);
		//set height box
		InputBox shb = new InputBox("set height", 0, 0);
		shb.addButton(new SetHeightAcceptButton(shb));
		shb.addButton(new CloseInputBoxButton(shb));
		ibce.registerInputBox(shb);
		//set map name
		InputBox smn = new InputBox("set map name", 0, 0);
		smn.addButton(new SetMapNameAcceptButton(smn));
		smn.addButton(new CloseInputBoxButton(smn));
		ibce.registerInputBox(smn);
	}
}