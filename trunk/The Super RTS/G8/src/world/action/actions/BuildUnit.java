package world.action.actions;

import sgEngine.BuildOrder;
import sgEngine.EngineConstants;
import sgEngine.SGEngine;
import utilities.Location;
import world.action.Action;
import world.owner.Owner;
import world.unit.Unit;

/**
 * builds units
 * @author Jack
 *
 */
public class BuildUnit extends Action
{
	String unitName;
	Location l;
	Owner owner;
	
	
	BuildOrder bo;
	SGEngine sge;
	boolean firstRun = true;
	
	/**
	 * creates a new build action
	 * @param unitName the unit to be built
	 * @param l the location of where the unit is to be built
	 * @param owner the owner of unit that is to be created
	 * @param sge a reference to the SGEngine to register the build and cancel
	 * it if necesary
	 */
	public BuildUnit(String unitName, Location l, Owner owner, SGEngine sge)
	{
		super("build unit");
		this.unitName = unitName;
		this.l = l;
		this.owner = owner;
		this.sge = sge;
	}
	public boolean performAction()
	{	
		if(firstRun)
		{
			Unit u = EngineConstants.unitFactory.makeUnit(unitName, null, null);
			l = new Location(l.x, u.getRestingHeight(), l.z);
			int runTime = sge.getIterationCount()+u.getBuildTime();
			bo = new BuildOrder(unitName, l, owner, runTime);
			sge.queueBuildOrder(bo);
			firstRun = false;
		}
		if(sge.getIterationCount() == bo.getRunTime())
		{
			return true;
		}
		return false;
	}
	public void cancelAction()
	{
		if(bo != null)
		{
			sge.cancelBuildOrder(bo.getBuildOrder(), bo.getRunTime());
		}
	}
}
