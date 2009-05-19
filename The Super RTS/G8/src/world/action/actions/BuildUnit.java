package world.action.actions;

import sgEngine.BuildOrder;
import sgEngine.SGEngine;
import utilities.Location;
import world.action.Action;
import world.owner.Owner;

/**
 * builds units
 * @author Jack
 *
 */
public class BuildUnit extends Action
{
	BuildOrder bo;
	SGEngine sge;
	
	/**
	 * creates a new build action
	 * @param unitName the unit to be built
	 * @param l the location of where the unit is to be built
	 * @param owner the owner of unit that is to be created
	 * @param sge a reference to the SGEngine to register the build and cancel
	 * it if necesary
	 * @param runTime when the unit is built and this unit is once again idle
	 */
	public BuildUnit(String unitName, Location l, Owner owner, int runTime, SGEngine sge)
	{
		super("build unit");
		bo = new BuildOrder(unitName, l, owner, runTime);
		this.sge = sge;
		sge.queueBuildOrder(bo);
	}
	public boolean performAction()
	{	
		if(sge.getIterationCount() == bo.getRunTime())
		{
			return true;
		}
		return false;
	}
	public void cancelAction()
	{
		sge.cancelBuildOrder(bo.getBuildOrder(), bo.getRunTime());
	}
}
