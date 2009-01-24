package permanents.terrain;

import world.permanent.Permanent;
import utilities.Location;
import java.awt.Graphics2D;
import graphics.Camera;
import java.awt.Point;
import java.awt.Color;

public class Dirt extends Permanent
{
	public Dirt(Location l)
	{
		super("dirt", l, 30, 30);
	}
	public void drawElementLG(Graphics2D g, Camera c)
	{
		Point p = c.getScreenLocation(l);
		g.setColor(new Color(132, 66, 0)); //brown
		g.fillRect(p.x-width/2, p.y-height/2, width, height);
	}
	public int getElementType() {
		return 2;
	}
}
