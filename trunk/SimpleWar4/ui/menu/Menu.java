package ui.menu;

import ui.button.Button;
import java.awt.*;

public abstract class Menu
{
	Button[] b = new Button[1];
	String name;
	int id;
	boolean visible = false;
	boolean movable = false;
	boolean headerClicked = false; //only used when movable
	
	protected int x;
	protected int y;
	protected int slotWidth;
	protected int slotHeight;
	
	public Menu(String name, int x, int y, int slotWidth, int slotHeight, int id)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.slotWidth = slotWidth;
		this.slotHeight = slotHeight;
		this.id = id;
	}
	public void setHeaderClicked(boolean setter)
	{
		headerClicked = setter;
	}
	public boolean getHeaderClicked()
	{
		return headerClicked;
	}
	public Rectangle getHeaderBounds()
	{
		return new Rectangle(x, y, slotWidth, slotHeight);
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
	public boolean getMovable()
	{
		return movable;
	}
	public void setMovable(boolean setter)
	{
		movable = setter;
	}
	public int getID()
	{
		return id;
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
	public void drawMenu(Graphics g)
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
