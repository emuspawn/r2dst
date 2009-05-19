package world.shot.weapon;

import world.shot.TestShot;

public class TestWeapon extends Weapon
{
	public TestWeapon()
	{
		super("test weapon", new TestShot(null), 100, 40);
	}
}
