package world;

import java.util.ArrayList;
import world.owner.Owner;
import world.unit.*;

/*
 * keeps track of all things being built, units or buildings
 */

public class BuildEngine
{
	UnitEngine ue;
	ArrayList<BuildItem> bi = new ArrayList<BuildItem>();
	
	public BuildEngine(UnitEngine ue)
	{
		this.ue = ue;
	}
	public void performBuildEngineFunctions()
	{
		for(int i = 0; i < bi.size(); i++)
		{
			bi.get(i).upCounter(ue);
			if(bi.get(i).getConstructed())
			{
				bi.remove(i);
			}
		}
	}
	public int startUnitConstruction(Owner owner, Unit unit)
	{
		//returns the build time for the unit
		bi.add(new BuildItem(this, unit, unit.getBuildTime()));
		unit.getOwner().setUnitCount(this, unit.getOwner().getUnitCount()+unit.getPopulationValue());
		unit.getOwner().setCurrentUnitMax(this, unit.getOwner().getCurrentUnitMax()+unit.getPopulationAugment());
		return unit.getBuildTime();
	}
}
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
	public void upCounter(UnitEngine ue)
	{
		counter++;
		if(counter == maxCounter)
		{
			ue.getUnits().add(u);
			constructed = true;
		}
	}
	public boolean getConstructed()
	{
		return constructed;
	}
}
