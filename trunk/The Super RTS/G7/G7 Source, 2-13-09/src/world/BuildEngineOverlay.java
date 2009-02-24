package world;

import world.unit.Unit;
import world.World;
import world.owner.Owner;

public class BuildEngineOverlay
{
	private BuildEngine be;
	
	public BuildEngineOverlay(World w)
	{
		this.be = w.getBuildEngine();
	}
	public int startUnitConstruction(Owner owner, Unit u)
	{
		//returns the build time for the unit
		return be.startUnitConstruction(owner, u);
	}
}
