package editor;

import java.awt.Point;

public abstract class Editor
{
	protected int editType;
	public Editor(){}
	public abstract void performEditorFunctions();
	public abstract void interpretMouseClick(Point p);
	public abstract void clearAllEditData();
	public void setEditType(int setter)
	{
		editType = setter;
	}
}
