package world.unit.units;

import world.unit.Unit;
import utilities.Location;
import owner.Owner;

public class Harvester extends Unit
{
	public Harvester(Owner owner, Location location)
	{
		super(owner, location, "harvester", 10, 130, 0, 0, 7, 25);
		movement = 9;
		isGatherer = true;
		gatheringRange = 15;
	}
}
