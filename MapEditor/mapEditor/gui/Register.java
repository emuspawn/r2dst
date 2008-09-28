package mapEditor.gui;

import ui.menu.*;
import mapEditor.gui.editorMenu.mainMenu.*;
import mapEditor.gui.editorMenu.chooserMenu.*;

public class Register implements MenuRegister
{
	public void registerMenus(MenuCheckEngine mce)
	{
		//main menus
		MenuGroup meg = new MenuGroup("main");
		meg.registerMenu(new MainEditorMenu(1));
		mce.registerMenuGroup(meg);
		//chooser menus
		MenuGroup cg = new MenuGroup("chooser");
		cg.registerMenu(new ChooserMenu(1));
		mce.registerMenuGroup(cg);
	}
}
