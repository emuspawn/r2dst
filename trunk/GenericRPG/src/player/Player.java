package player;

import java.awt.*;

public class Player
{
	int x, y, speed;
	
	public Player()
	{
		x = 30;
		y = 30;
		speed = 5;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void move(int xAdd, int yAdd)
	{
		x += xAdd;
		y += yAdd;
	}
	
	public void moveTo(int newX, int newY)
	{
		x = newX;
		y = newY;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillOval(x-15,y-15,30,30);
	}
}
