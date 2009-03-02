package world.unit.units;

import world.unit.Unit;
import utilities.Location;
import world.owner.Owner;

public class Harvester extends Unit
{
	public Harvester(Owner owner, Location location)
	{
		super(owner, location, "harvester", 10, 130, null, 7, 25);
		movement = 9;
		isGatherer = true;
	}
}
