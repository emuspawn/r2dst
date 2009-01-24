package world.environment;

import java.io.Serializable;

import world.Element;
import world.destructable.Destructable;
import utilities.Location;

/*
 * essentially a timed destructable, effects destructables in a specific region until
 * destroyed or times runs out
 */

public abstract class EnvironmentalEffect extends Destructable implements Serializable
{
	private static final long serialVersionUID = 1L;
	public EnvironmentalEffect(String name, Location l, int width, int height)
	{
		super(name, l, width, height);
	}
	public abstract void processEffect(Element e);
}
