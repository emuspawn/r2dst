package gameEngine.world.weapon.weapons;

import gameEngine.world.owner.Owner;
import gameEngine.world.shot.Shot;
import gameEngine.world.shot.shots.LightRound;
import gameEngine.world.unit.Unit;
import gameEngine.world.weapon.Weapon;

public class MachineGun extends Weapon
{
	public MachineGun()
	{
		super(100);
	}
	protected Shot getShot(double x, double y, Unit target, Owner o)
	{
		return new LightRound(x, y, target, o);
	}
}
