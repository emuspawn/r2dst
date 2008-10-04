package ui;

import controller.*;

public interface Actionable
{
	public void performAction(RPGController rpgc);
	public void performAction(MapEditorController mec);
	public void performAction(WorldEditorController wec);
}
