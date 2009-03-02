package world.unit.units;

import world.unit.Unit;
import world.shot.weapon.MediumTurret;
import utilities.Location;
import world.owner.Owner;

/**
 * basic fighting unit
 * @author Jack
 *
 */
public class Fighter extends Unit
{
	public Fighter(Owner owner, Location location)
	{
		super(owner, location, "fighter", 10, 120, new MediumTurret(), 6, 20);
		movement = 6;
	}
}
