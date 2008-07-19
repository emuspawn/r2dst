package ui.button;

import java.awt.Color;
import java.awt.Graphics;

import world.World;

public class Separator extends Button
{
	public Separator(int x, int y, int width, int height)
	{
		super("-------", x, y, width, height);
	}
	public void performAction(World w){}
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
