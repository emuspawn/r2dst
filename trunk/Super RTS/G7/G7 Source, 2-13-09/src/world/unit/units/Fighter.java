package world.unit.units;

import world.unit.Unit;
import utilities.Location;
import world.owner.Owner;

public class Fighter extends Unit
{
	public Fighter(Owner owner, Location location)
	{
		super(owner, location, "fighter1", 10, 120, 90, 4, 6, 20);
	}
	public boolean is(String type)
	{
		if(type.equalsIgnoreCase("fighter") || type.equalsIgnoreCase(getName()))
		{
			return true;
		}
		return false;
	}
}
