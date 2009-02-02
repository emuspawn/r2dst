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

	@Override
	public void drawElementLG(Graphics2D g) {
		g.setColor(new Color(132, 66, 0));
		g.fillRect((int)l.x-width/2, (int)l.y-height/2, width, height);
	}
}
