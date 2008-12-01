package world;

import world.unit.Unit;
import owner.Owner;

public class BuildEngineOverlay
{
	private BuildEngine be;
	
	public BuildEngineOverlay(BuildEngine be)
	{
		this.be = be;
	}
	public int startUnitConstruction(Owner owner, Unit u)
	{
		//returns the build time for the unit
		return be.startUnitConstruction(owner, u);
	}
}
