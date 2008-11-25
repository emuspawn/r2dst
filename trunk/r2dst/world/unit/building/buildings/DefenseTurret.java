package world.unit.building.buildings;

import world.unit.building.Building;
import utilities.Location;
import owner.Owner;

public class DefenseTurret extends Building
{
	public DefenseTurret(Owner owner, Location location)
	{
		super(owner, location, "defense turret", 30, 200, 20, 30);
		range = 110;
		damage = 6;
	}
}
