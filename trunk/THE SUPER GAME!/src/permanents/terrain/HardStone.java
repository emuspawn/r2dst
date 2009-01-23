package permanents.terrain;

import world.permanent.Permanent;
import utilities.Location;
import java.awt.Graphics2D;
import graphics.Camera;
import java.awt.Point;
import java.awt.Color;

/*
 * hard because cannot be destroyed
 */

public class HardStone extends Permanent
{
	public HardStone(Location l)
	{
		super("hard stone", l, 30, 30);
		impassable = true;
	}
	public void drawElementLG(Graphics2D g, Camera c)
	{
		Point p = c.getScreenLocation(l);
		g.setColor(new Color(140, 140, 140)); //brown
		g.fillRect(p.x-width/2, p.y-height/2, width, height);
	}
}
