package ui.inputBox;

import java.awt.Point;
import java.awt.Graphics;
import controller.*;
import ui.button.*;

public class InputBoxCheckEngine
{
	RPGController rpgc;
	MapEditorController mec;
	
	InputBox[] ib = new InputBox[10];
	
	int gameType;
	
	/*
	 * gameType:
	 * 1=rpg
	 * 2=map editor
	 */
	
	public InputBoxCheckEngine(RPGController rpgc, InputBoxRegister ibr)
	{
		this.rpgc = rpgc;
		gameType = 2;
		ibr.registerInputBoxes(this);
	}
	public InputBox getInputBox(String name)
	{
		for(int i = 0; i < ib.length; i++)
		{
			if(ib[i] != null)
			{
				if(ib[i].getName().equalsIgnoreCase(name))
				{
					return ib[i];
				}
			}
		}
		return null;
	}
	public InputBoxCheckEngine(MapEditorController mec, InputBoxRegister ibr)
	{
		this.mec = mec;
		gameType = 2;
		ibr.registerInputBoxes(this);
	}
	public void performInputBoxCheckFunctions()
	{
		Button[] b;
		for(int i = 0; i < ib.length; i++)
		{
			if(ib[i] != null)
			{
				b = ib[i].getButtons();
				for(int a = 0; a < b.length; a++)
				{
					if(b[a] != null)
					{
						if(b[a].getClicked())
						{
							b[a].setClicked(false);
							if(gameType == 1)
							{
								b[a].performAction(rpgc);
							}
							else if(gameType == 2)
							{
								b[a].performAction(mec);
							}
						}
					}
				}
			}
		}
	}
	public void testForInputBoxButtonClick(Point p)
	{
		Button[] b;
		for(int i = 0; i < ib.length; i++)
		{
			if(ib[i] != null)
			{
				b = ib[i].getButtons();
				if(b != null)
				{
					for(int a = 0; a < b.length; a++)
					{
						if(b[a] != null)
						{
							if(b[a].getBounds().contains(p))
							{
								b[a].setClicked(true);
							}
						}
					}
				}
			}
		}
	}
	public void drawInputBoxes(Graphics g)
	{
		for(int i = 0; i < ib.length; i++)
		{
			if(ib[i] != null)
			{
				ib[i].drawInputBox(g);
			}
		}
	}
	public void registerInputBox(InputBox ibx)
	{
		boolean added = false;
		for(int i = 0; i < ib.length; i++)
		{
			if(ib[i] == null)
			{
				ib[i] = ibx;
				added = true;
				break;
			}
		}
		if(!added)
		{
			enlargeInputBoxArray();
			registerInputBox(ibx);
		}
	}
	private void enlargeInputBoxArray()
	{
		InputBox[] temp = new InputBox[ib.length+1];
		for(int i = 0; i < ib.length; i++)
		{
			temp[i] = ib[i];
		}
		ib = temp;
	}
	public InputBox getVisibleInputBox()
	{
		for(int i = 0; i < ib.length; i++)
		{
			if(ib[i] != null)
			{
				if(ib[i].getVisible())
				{
					return ib[i];
				}
			}
		}
		return null;
	}
}
