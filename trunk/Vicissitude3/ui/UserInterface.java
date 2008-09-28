package ui;

import java.awt.Point;
import ui.button.Button;

public abstract class UserInterface
{
	protected String name;
	protected Button[] b = new Button[1];
	protected int x;
	protected int y;
	protected int slotWidth; //the width of the buttons for the interface
	protected int slotHeight;
	protected boolean visible = false;
	
	public UserInterface(String name, int x, int y, int slotWidth, int slotHeight)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.slotWidth = slotWidth;
		this.slotHeight = slotHeight;
	}
	public void setLocation(Point p)
	{
		int xdiff = p.x - x;
		int ydiff = p.y - y;
		
		for(int i = 0; i < b.length; i++)
		{
			if(b[i] != null)
			{
				b[i].translateButton(xdiff, ydiff);
			}
		}
		
		x = p.x;
		y = p.y;
	}
	public String getName()
	{
		return name;
	}
	public void testButtonsForClick(Point p)
	{
		for(int i = 0; i < b.length; i++)
		{
			if(b[i] != null)
			{
				if(b[i].getBounds().contains(p))
				{
					b[i].setClicked(true);
					break;
				}
			}
		}
	}
	public boolean getVisible()
	{
		return visible;
	}
	public void setVisible(boolean setter)
	{
		visible = setter;
	}
	public void addButton(Button button)
	{
		b[b.length-1] = button;
		enlargeButtonArray();
	}
	private void enlargeButtonArray()
	{
		Button[] temp = new Button[b.length+1];
		for(int i = 0; i < b.length; i++)
		{
			temp[i] = b[i];
		}
		b = temp;
	}
	public Button[] getButtons()
	{
		return b;
	}
	public Point getLocation()
	{
		return new Point(x, y);
	}
	public int getSlotWidth()
	{
		return slotWidth;
	}
	public int getSlotHeight()
	{
		return slotHeight;
	}
	public void setSlotWidth(int setter)
	{
		slotWidth = setter;
	}
	public void setSlotHeight(int setter)
	{
		slotHeight = setter;
	}
}
