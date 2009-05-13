package units;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Unit
{
	protected int x, y, life, maxLife, mana, maxMana, width;
	protected int moveSpeed = 0;
	public static Random r = new Random(); //random generator for all units
	
	public Unit(int x, int y, int maxLife, int maxMana, int width)
	{
		this.x = x;
		this.y = y;
		this.maxLife = maxLife;
		life = maxLife;
		this.maxMana = maxMana;
		mana = maxMana;
		this.width = width;
	}
	
	public void act()
	{
		
	}
	
	public void move(int xAdd, int yAdd)
	{
		x += xAdd;
		y += yAdd;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void moveTo(int newX, int newY)
	{
		x = newX;
		y = newY;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getLife()
	{
		return life;
	}
	
	public void setLife(int newLife)
	{
		life = newLife;
	}
	public int getMaxLife()
	{
		return maxLife;
	}
	
	public void setMaxLife(int newMaxLife)
	{
		maxLife = newMaxLife;
	}
	
	public int getMana()
	{
		return mana;
	}
	
	public void setMana(int newMana)
	{
		mana = newMana;
	}
	public int getMaxMana()
	{
		return maxMana;
	}
	
	public void setMaxMana(int newMaxMana)
	{
		maxMana = newMaxMana;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillOval(x-width/2,y-width/2,width,width);
	}
}
