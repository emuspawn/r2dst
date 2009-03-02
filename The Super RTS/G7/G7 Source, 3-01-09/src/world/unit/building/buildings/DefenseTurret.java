package world.unit.building.buildings;

import world.unit.building.Building;
import world.shot.weapon.HeavyTurret;
import utilities.Location;
import world.owner.Owner;

public class DefenseTurret extends Building
{
	public DefenseTurret(Owner owner, Location location)
	{
		super(owner, location, "defense turret", 30, 200, 17, 30);
		weapon = new HeavyTurret();
	}
}
