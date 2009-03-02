package world.unit.units;

import java.util.ArrayList;
import world.shot.weapon.LightTurret;
import world.unit.BuildTree;
import world.unit.Unit;
import utilities.Location;
import world.owner.Owner;

/**
 * a medium builder
 * @author Jack
 *
 */
public class Engineer extends Unit
{
	public Engineer(Owner owner, Location location)
	{
		super(owner, location, "engineer", 6, 80, new LightTurret(), 6, 20);
		
		ArrayList<String> u = new ArrayList<String>();
		u.add("factory");
		u.add("defense turret");
		BuildTree tree = new BuildTree(u);
		bt = tree;
		
		isBuilder = true;
	}
}
