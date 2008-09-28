package mapEditor.gui.editorMenu.chooserMenu;

import ui.menu.*;
import mapEditor.gui.editorButton.chooserButton.*;
import ui.button.*;

public class ChooserMenu extends Menu
{
	public ChooserMenu(int id)
	{
		super("Terrain Chooser", 0, 0, 150, 20, id);
		addButton(new PlaceWallButton(this));
		addButton(new PlaceFloorButton(this));
		addButton(new PlaceDirtButton(this));
		addButton(new PlaceWaterButton(this));
		addButton(new Separator(this));
		addButton(new CloseChooserButton(this));
	}
}
