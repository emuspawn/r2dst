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
	public void drawElementLG(Graphics2D g)
	{
		g.setColor(new Color(140, 140, 140)); //brown
		g.fillRect((int)l.x-width/2, (int)l.y-height/2, width, height);
	}
	public int getElementType()
	{
		return 3;
	}
}
