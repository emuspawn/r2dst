package ui.button;

import java.awt.Rectangle;

public class Button
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
}
