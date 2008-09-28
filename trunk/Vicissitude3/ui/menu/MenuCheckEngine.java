package ui.menu;

import controller.*;
import java.awt.Graphics;
import java.awt.Point;

//checks and runs menu and browser functions

public class MenuCheckEngine
{
	RPGController rpgc;
	MapEditorController mec;
	
	MenuGroup[] m = new MenuGroup[10];
	int gameType;
	
	/*
	 * gameType:
	 * 1=rpg
	 * 2=map editor
	 */
	
	public MenuCheckEngine(RPGController rpgc, MenuRegister mr)
	{
		this.rpgc = rpgc;
		gameType = 1;
		mr.registerMenus(this);
	}
	public MenuCheckEngine(MapEditorController mec, MenuRegister mr)
	{
		this.mec = mec;
		gameType = 2;
		mr.registerMenus(this);
	}
	public MenuGroup[] getMenuGroups()
	{
		return m;
	}
	public MenuGroup getMenuGroup(String name)
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				if(m[i].getName().equalsIgnoreCase(name))
				{
					return m[i];
				}
			}
		}
		return null;
	}
	public void testMenuGroupsForClick(Point p)
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				m[i].testMenusForClick(p);
			}
		}
	}
	public void registerMenuGroup(MenuGroup menuGroup)
	{
		boolean added = false;
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] == null)
			{
				m[i] = menuGroup;
				added = true;
				break;
			}
		}
		if(!added)
		{
			enlargeMenuGroupArray();
			registerMenuGroup(menuGroup);
		}
	}
	private void enlargeMenuGroupArray()
	{
		MenuGroup[] temp = new MenuGroup[m.length+5];
		for(int i = 0; i < m.length; i++)
		{
			temp[i] = m[i];
		}
		m = temp;
	}
	private void performMenuGroupFunctions()
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				if(gameType == 1)
				{
					m[i].performMenuGroupFunctions(rpgc);
				}
				else if(gameType == 2)
				{
					m[i].performMenuGroupFunctions(mec);
				}
			}
		}
	}
	public void performMenuCheckFunctions()
	{
		performMenuGroupFunctions();
	}
	public void drawMenuGroups(Graphics g)
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				m[i].drawMenus(g);
			}
		}
	}
}
