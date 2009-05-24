package world.action.actions;

import sgEngine.SGEngine;
import utilities.Location;
import world.action.ActionList;
import world.unit.Unit;

/**
 * the action that sends a unit to build something
 * @author Jack
 *
 */
public class Build extends ActionList
{
	/**
	 * creates a new build action
	 * @param builder the builder
	 * @param name the name of the unit to be built
	 * @param location the location of where the unit is to be built at
	 * @param runTime when the unit is to be finished
	 * @param sge a reference to the SGEngine to queue the build order
	 */
	public Build(Unit builder, String name, Location location, SGEngine sge)
	{
		super("build");
		addActionToList(new Move(builder, location));
		addActionToList(new BuildUnit(name, location, builder.getOwner(), sge));
	}
}
