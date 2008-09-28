package ui.menu;

import ui.button.*;
import java.awt.Graphics;
import java.awt.Point;
import controller.*;

/*
 * groups menus together for easy use
 * 
 * ex, user clicks on something requiring the closing
 * of a group of menus concerning one part of the
 * application
 */

public class MenuGroup
{
	Menu[] m = new Menu[10];
	String name; //idetifies the menu group
	
	public MenuGroup(String name)
	{
		this.name = name;
		m = new Menu[10];
	}
	public void hideAllMenus()
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				m[i].setVisible(false);
			}
		}
	}
	public String getName()
	{
		return name;
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
	public void registerMenu(Menu menu)
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
	public void testMenusForClick(Point p)
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				if(m[i].getVisible())
				{
					m[i].testButtonsForClick(p);
				}
			}
		}
	}
	public void performMenuGroupFunctions(RPGController c)
	{
		performButtonActions(c);
	}
	public void performMenuGroupFunctions(MapEditorController c)
	{
		performButtonActions(c);
	}
	private void performButtonActions(MapEditorController c)
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
							b[a].performAction(c);
						}
					}
				}
			}
		}
	}
	private void performButtonActions(RPGController c)
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
							b[a].performAction(c);
						}
					}
				}
			}
		}
	}
	public void drawMenus(Graphics g)
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				m[i].drawMenu(g);
			}
		}
	}
}

