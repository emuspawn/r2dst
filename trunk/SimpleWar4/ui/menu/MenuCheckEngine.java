package ui.menu;

import ui.button.*;
import world.World;
import ui.menu.editorMenus.mapEditorV1Menus.*;
import ui.menu.gameMenus.*;
import ui.menu.gameSetupMenus.*;

public class MenuCheckEngine
{
	Menu[] m = new Menu[10];
	World w;
	
	public MenuCheckEngine(World w)
	{
		this.w = w;
		setupMenuCheckEngine();
	}
	public void setupMenuCheckEngine()
	{
		int rm = w.getRunSpecification().getMode();
		m = new Menu[10];
		if(rm == 1)
		{
			registerMapEditorV2Menus();
		}
		else if(rm == 2)
		{
			registerGameMenus();
		}
		else if(rm == 3)
		{
			registerGameSetupMenus();
		}
	}
	private void registerGameSetupMenus()
	{
		registerMenu(new GameSetupMenu(1));
		registerMenu(new SkirmishMenu(2));
		registerMenu(new NormalSkirmishGameSetupMenu(3));
		registerMenu(new PlayerAmountMenu(4));
		registerMenu(new SelectPlayerEditMenu(5));
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
	private void registerGameMenus()
	{
		registerMenu(new MainGameMenu(1));
	}
	private void registerMapEditorV2Menus()
	{
		registerMenu(new MainEditMenu(1));
		registerMenu(new TerrainEditMenu(2));
		registerMenu(new PolygonDrawAcceptMenu(3));
		registerMenu(new YesNoClearAllEditDataMenu(4));
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
		deselectHeadersForNonVisibleMenus();
	}
	private void deselectHeadersForNonVisibleMenus()
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null)
			{
				if(m[i].getHeaderClicked())
				{
					if(!m[i].getVisible())
					{
						m[i].setHeaderClicked(false);
					}
				}
			}
		}
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
