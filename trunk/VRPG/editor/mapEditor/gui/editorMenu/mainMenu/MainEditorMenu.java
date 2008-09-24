package editor.mapEditor.gui.editorMenu.mainMenu;

import ui.menu.*;
import editor.mapEditor.gui.editorButton.mainButton.*;
import ui.button.*;

public class MainEditorMenu extends Menu
{
	public MainEditorMenu(int id)
	{
		super("Main Menu", 0, 0, 150, 20, id);
		addButton(new EditMapNameButton(this));
		addButton(new EditMapWidthButton(this));
		addButton(new EditMapHeightButton(this));
		addButton(new Separator(this));
		addButton(new SaveMapButton(this));
		addButton(new SaveMapPicButton(this));
		addButton(new LoadMapButton(this));
		addButton(new Separator(this));
		addButton(new ExitButton(this));
	}
}