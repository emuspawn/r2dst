package factory;

import world.resource.*;
import utilities.Location;

/**
 * creates game resources
 * @author Jack
 *
 */
public final class ResourceFactory
{
	/**
	 * creates a game resource
	 * @param name the name of the resource to be created
	 * @param l the location at which the resource is to be created
	 * @return returns a new resource
	 */
	public static Resource makeResource(String name, Location l)
	{
		if(name.equalsIgnoreCase("tree"))
		{
			return new Tree(l);
		}
		return null;
	}
}
