package ui.button;

import java.awt.Rectangle;
import ui.Actionable;
import ui.menu.*;
import java.awt.Graphics;
import java.awt.Color;
import controller.*;
import ui.inputBox.*;
import ui.UserInterface;

public abstract class Button implements Actionable
{
	boolean clicked = false;
	String name;
	String ibOwner; //input box owner
	
	//rectangle variables
	int x;
	int y = 500;
	int width;
	int height;
	
	public Button(String name, Menu m)
	{
		this.x = m.getLocation().x;
		this.width = m.getSlotWidth();
		this.height = m.getSlotHeight();
		this.name = name;
		
		setupButtonHeight(m);
	}
	public Button(String name, InputBox ib)
	{
		this.x = ib.getLocation().x+ib.getInputBoxWidth();
		this.width = ib.getSlotWidth();
		this.height = ib.getSlotHeight();
		this.name = name;
		ibOwner = ib.getName();
		
		setupButtonHeight(ib);
	}
	private void setupButtonHeight(UserInterface ui)
	{
		Button[] b = ui.getButtons();
		for(int i = 0; i < b.length; i++)
		{
			if(b[i] == null)
			{
				y = height*(i+1);
				break;
			}
		}
	}
	public String getInputBoxOwner()
	{
		return ibOwner;
	}
	public void translateButton(int xover, int yover)
	{
		x = x + xover;
		y = y + yover;
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
	public void performAction(RPGController rpgc){} //to be overriden in the extending class
	public void performAction(MapEditorController mec){}
	public void performAction(WorldEditorController wec){}
	public void drawButton(Graphics g)
	{
		if(clicked)
		{
			g.setColor(Color.orange);
		}
		else
		{
			g.setColor(Color.lightGray);
		}
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		g.drawString(name, x+3, y+16);
	}
}
