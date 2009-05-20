package sgEngine;

import utilities.Location;
import world.owner.Owner;

/**
 * represents an order in the main engine for a unit
 * at a specified time
 * @author Jack
 *
 */
public final class BuildOrder
{
	private String build;
	private int runTime;
	private Location l;
	private Owner owner;
	
	/**
	 * creates a new build order
	 * @param build the unit to be built
	 * @param l the location where the unit is to be built
	 * @param owner the owner of the unit to be created
	 * @param runTime when the build order is to be run
	 */
	public BuildOrder(String build, Location l, Owner owner, int runTime)
	{
		this.build = build;
		this.runTime = runTime;
		this.l = l;
		this.owner = owner;
	}
	/**
	 * gets the owner of the unit that is to be created
	 * @return
	 */
	public Owner getOwner()
	{
		return owner;
	}
	/**
	 * gets the location of where the unit is to placed in the world
	 * after it is created
	 * @return returns the build location
	 */
	public Location getLocation()
	{
		return l;
	}
	/**
	 * gets the iteration the build order should finish on
	 * @return
	 */
	public int getRunTime()
	{
		return runTime;
	}
	/**
	 * gets what was ordered to be build
	 * @return
	 */
	public String getBuildOrder()
	{
		return build;
	}
}
