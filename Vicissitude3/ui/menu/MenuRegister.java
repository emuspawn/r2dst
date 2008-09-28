package ui.menu;

/*
 * sets ip all menus and menu groups, called by MenuCheckEngine and register all
 * its menus to it
 */

public interface MenuRegister
{
	public void registerMenus(MenuCheckEngine mce);
}
