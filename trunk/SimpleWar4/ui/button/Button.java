package ui.button;

import java.awt.Rectangle;
import world.World;
import java.awt.Graphics;
import java.awt.Color;

public abstract class Button
{
	boolean clicked = false;
	String name;
	
	//rectangle variables
	int x;
	int y;
	int width;
	int height;
	
	public Button(String name, int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
	}
	public boolean getClicked()
	{
		return clicked;
	}
	public void setClicked(boolean setter)
	{
		clicked = setter;
	}
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}
	public abstract void performAction(World w);
	public void drawButton(Graphics g)
	{
		g.setColor(Color.lightGray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		g.drawString(name, x+3, y+16);
	}
}
