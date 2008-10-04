package ui.inputBox;

import ui.*;
import java.awt.Graphics;
import java.awt.Color;
import controller.*;

public class InputBox extends UserInterface implements Actionable
{
	String s = new String("");
	int ibw = 200; //input box width
	
	public InputBox(String name, int x, int y)
	{
		super(name, x, y, 100, 20);
	}
	public String getText()
	{
		return s;
	}
	public int getInputBoxWidth()
	{
		return ibw;
	}
	public void resetString()
	{
		s = new String("");
	}
	public void drawInputBox(Graphics g)
	{
		if(visible)
		{
			g.setColor(Color.lightGray);
			g.fillRect(x, y, ibw, 100);
			g.setColor(Color.black);
			g.drawRect(x, y, ibw, 100);
			
			int section = 32; //how long a section the string is broken into to be displayed
			String temp = s;
			while(temp.length() % section != 0)
			{
				temp+=" ";
			}
			int line = 1;
			for(int i = 0; i < temp.length(); i+=section)
			{
				g.drawString(temp.substring(i, i+section), x+3, line*16);
				line++;
			}
			for(int i = 0; i < b.length; i++)
			{
				if(b[i] != null)
				{
					b[i].drawButton(g);
				}
			}
		}
	}
	public void appendCharacter(char c)
	{
		s+=c;
	}
	public void performAction(RPGController rpgc){} //to be overriden in the extending class
	public void performAction(MapEditorController mec){}
	public void performAction(WorldEditorController wec){}
}
