package weapons;

import java.awt.Graphics;

public class Shot
{
	public int damage, x, y, hspeed, vspeed, range;
	int timer = 0;
	
	public Shot(int x, int y, int d, int hs, int vs, int r)
	{
		damage = d;
		this.x = x;
		this.y = y;
		hspeed = hs;
		vspeed = vs;
		range = r;
	}
	
	public boolean move()
	{
		timer++;
		if (timer >= range)
			return true;
		x += hspeed;
		y += vspeed;
		return false;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void draw(Graphics g)
	{
		
	}
}
