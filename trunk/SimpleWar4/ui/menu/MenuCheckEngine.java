package ui.menu;

import ui.button.*;
import world.World;
import ui.menu.editorMenus.*;

public class MenuCheckEngine
{
	Menu[] m = new Menu[10];
	World w;
	
	public MenuCheckEngine(World w)
	{
		this.w = w;
		if(w.getEditMode())
		{
			registerEditMenus();
		}
	}
	public Menu[] getMenus()
	{
		return m;
	}
	public Menu getMenu(int id)
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				if(m[i].getID() == id)
				{
					return m[i];
				}
			}
		}
		return null;
	}
	private void registerEditMenus()
	{
		registerMenu(new MainEditMenu(1));
	}
	private void registerMenu(Menu menu)
	{
		boolean added = false;
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] == null)
			{
				m[i] = menu;
				added = true;
				break;
			}
		}
		if(!added)
		{
			enlargeMenuArray();
			registerMenu(menu);
		}
	}
	private void enlargeMenuArray()
	{
		Menu[] temp = new Menu[m.length+5];
		for(int i = 0; i < m.length; i++)
		{
			temp[i] = m[i];
		}
		m = temp;
	}
	public void performMenuCheckFunctions()
	{
		performButtonActions();
	}
	private void performButtonActions()
	{
		Button[] b;
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				b = m[i].getButtons();
				for(int a = 0; a < b.length; a++)
				{
					if(b[a] != null)
					{
						if(b[a].getClicked())
						{
							b[a].setClicked(false);
							b[a].performAction(w);
						}
					}
				}
			}
		}
	}
}
