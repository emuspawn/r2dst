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
		shapeType = 1;
		clr = new Color(140, 140, 140);
	}
}
