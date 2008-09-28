package ui.button;

import java.awt.Color;
import ui.menu.*;
import java.awt.Graphics;

public class Separator extends Button
{
	public Separator(Menu m)
	{
		super("-------", m);
	}
	public void performAction(Object o){}
	public void drawButton(Graphics g)
	{
		//does not draw orange briefly when clicked
		g.setColor(Color.lightGray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		g.drawString(name, x+3, y+16);
	}
}
