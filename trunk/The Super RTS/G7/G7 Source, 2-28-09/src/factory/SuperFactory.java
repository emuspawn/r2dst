package factory;

import utilities.Location;
import world.Element;

/**
 * encompasses all other factories for editing purposes, should not be called under
 * normal circumstances because it must sort through all other factories to determine
 * the Element is creates and returns, it is not as fast as using a single factory that
 * creates the Element that you are trying to create
 * @author Jack
 *
 */
public final class SuperFactory
{
	public static Element makeElement(String name, Location l, int width, int height)
	{
		Element e = TerrainFactory.makeTerrain(name, l, width, height);
		if(e == null)
		{
			e = ResourceFactory.makeResource(name, l);
		}
		
		return e;
	}
}
