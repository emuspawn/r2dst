package world.shot;

import utilities.Location;
import world.owner.Owner;

public class TestShot extends Shot
{
	public TestShot(Owner owner)
	{
		super(new Location(0, 0, 0), null, owner, 10, 6, 2, 2, 4);
	}

}
