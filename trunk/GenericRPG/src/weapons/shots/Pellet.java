package weapons.shots;

import java.awt.Color;
import java.awt.Graphics;

import weapons.Shot;

public class Pellet extends Shot
{
	public Pellet(int x, int y, int hspeed, int vspeed)
	{
		super(x, y, 2, hspeed, vspeed, 50);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 4, 4);
	}
}
