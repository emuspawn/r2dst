package world.permanent;

import world.Element;
import utilities.Location;

/*
 * a permanent feature of the world, will not change, mainly terrain such as rock and or water is permanent
 */

public abstract class Permanent extends Element
{
	public Permanent(String name, Location l, int width, int height)
	{
		super(name, l, width, height);
	}
}
