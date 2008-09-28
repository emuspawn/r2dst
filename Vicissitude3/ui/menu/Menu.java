package ui.menu;

import ui.UserInterface;
import java.awt.*;

public abstract class Menu extends UserInterface
{
	int id;
	boolean movable = false;
	
	public Menu(String name, int x, int y, int slotWidth, int slotHeight, int id)
	{
		super(name, x, y, slotWidth, slotHeight);
		this.name = name;
		this.id = id;
	}
	public Point getRightSidePoint()
	{
		//returns the location of the top right corner of the menu so other menus can start adjacent to it
		return new Point(x+slotWidth, y);
	}
	public Rectangle getHeaderBounds()
	{
		return new Rectangle(x, y, slotWidth, slotHeight);
	}
	public int getID()
	{
		return id;
	}
	public void drawMenu(Graphics g)
	{
		if(visible)
		{
			g.setColor(Color.lightGray);
			g.fillRect(x, y, slotWidth, slotHeight);
			g.setColor(Color.black);
			g.drawRect(x, y, slotWidth, slotHeight);
			g.drawRect(x, y, slotWidth, slotHeight-1);
			g.drawString(name, x+3, y+16);
			
			for(int i = 0; i < b.length; i++)
			{
				if(b[i] != null)
				{
					b[i].drawButton(g);
				}
			}
		}
	}
}
