package world;

import java.util.ArrayList;
import world.owner.Owner;
import world.unit.Unit;

/*
 * keeps track of all things being built, units or buildings
 */

public class BuildEngine
{
	World w;
	ArrayList<BuildItem> bi = new ArrayList<BuildItem>();
	
	public BuildEngine(World w)
	{
		this.w = w;
	}
	public void performBuildEngineFunctions()
	{
		for(int i = 0; i < bi.size(); i++)
		{
			bi.get(i).upCounter(w);
			if(bi.get(i).getConstructed())
			{
				bi.remove(i);
			}
		}
	}
	/**
	 * creates a build item for the passed unit
	 * @param owner the owner of the unit being built
	 * @param unit the unit to be built
	 * @return returns the build time of the unit being built
	 */
	public int startUnitConstruction(Owner owner, Unit unit)
	{
		//returns the build time for the unit
		bi.add(new BuildItem(this, unit, unit.getBuildTime()));
		unit.getOwner().setUnitCount(this, unit.getOwner().getUnitCount()+unit.getPopulationValue());
		unit.getOwner().setCurrentUnitMax(this, unit.getOwner().getCurrentUnitMax()+unit.getPopulationAugment());
		return unit.getBuildTime();
	}
}
/**
 * a counter that constructs a unit
 * @author Jack
 *
 */
class BuildItem
{
	//includes a counter for when the unit is done and the unit itself
	int maxCounter;
	int counter = 0;
	Unit u;
	boolean constructed = false;
	BuildEngine be;
	
	public BuildItem(BuildEngine be, Unit u, int buildTime)
	{
		this.be = be;
		this.u = u;
		maxCounter = buildTime;
	}
	/**
	 * increments the build counter, after counter
	 * maxes out unit is "built" (registered with
	 * the world) and the build item sets itself
	 * up for deletion
	 * @param w the world that the unit will be registered with
	 */
	public void upCounter(World w)
	{
		counter++;
		if(counter == maxCounter)
		{
			w.registerElement(u);
			//ue.getUnits().add(u);
			constructed = true;
		}
	}
	public boolean getConstructed()
	{
		return constructed;
	}
}
