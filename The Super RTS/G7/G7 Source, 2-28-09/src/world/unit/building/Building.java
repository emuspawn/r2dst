package world.unit.building;

import world.unit.Unit;
import utilities.Location;
import world.owner.Owner;

public class Building extends Unit
{
	public Building(Owner owner, Location location, String name, int maxLife, int viewRange, int cost, int size)
	{
		super(owner, location, name, maxLife, viewRange, 0, 0, cost, size);
		isBuilding = true;
		movement = 0;
	}
}
