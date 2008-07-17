package ui.menu;

import ui.button.Button;
import java.awt.*;

public abstract class Menu
{
	Button[] b = new Button[1];
	String name;
	int id;
	
	int x;
	int y;
	int slotWidth;
	int slotHeight;
	
	public Menu(String name, int x, int y, int slotWidth, int slotHeight, int id)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.slotWidth = slotWidth;
		this.slotHeight = slotHeight;
		this.id = id;
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
		int height = slotHeight * (b.length-1);
		g.fillRect(x, y, slotWidth, height);
	}
}
