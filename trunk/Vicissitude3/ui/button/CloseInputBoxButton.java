package ui.button;

import controller.MapEditorController;
import controller.RPGController;
import ui.inputBox.*;

public class CloseInputBoxButton extends Button
{
	InputBox ib;
	public CloseInputBoxButton(InputBox ib)
	{
		super("Close", ib);
		this.ib = ib;
	}
	public void performAction(RPGController rpgc)
	{
		ib.setVisible(false);
		ib.resetString();
	}
	public void performAction(MapEditorController mec)
	{
		ib.setVisible(false);
		ib.resetString();
	}
}
