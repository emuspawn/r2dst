package ui;

import ui.menu.*;
import java.awt.Graphics;
import ui.inputBox.*;
import java.awt.Point;

/*
 * meant to simplify ui management, all ui functions and click checks performed
 * in just one method apiece, must feed the class the constructed ui engines
 */

public class UICheckEngine
{
	MenuCheckEngine mce;
	InputBoxCheckEngine ibce;
	
	public UICheckEngine(MenuCheckEngine mce, InputBoxCheckEngine ibce)
	{
		this.mce = mce;
		this.ibce = ibce;
	}
	public MenuCheckEngine getMenuCheckEngine()
	{
		return mce;
	}
	public InputBoxCheckEngine getInputBoxCheckEngine()
	{
		return ibce;
	}
	public void performUIFunctions()
	{
		mce.performMenuCheckFunctions();
		ibce.performInputBoxCheckFunctions();
	}
	public void testUIForClick(Point p)
	{
		mce.testMenuGroupsForClick(p);
		ibce.testForInputBoxButtonClick(p);
	}
	public void testUIForKeyInput(char c)
	{
		InputBox ib = ibce.getVisibleInputBox();
		if(ib != null)
		{
			ib.appendCharacter(c);
		}
	}
	public void drawUI(Graphics g)
	{
		mce.drawMenuGroups(g);
		ibce.drawInputBoxes(g);
	}
}