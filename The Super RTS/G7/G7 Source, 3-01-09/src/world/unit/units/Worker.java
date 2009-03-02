package world.unit.units;

import java.util.ArrayList;
import world.shot.weapon.LightTurret;
import world.unit.BuildTree;
import world.unit.Unit;
import utilities.Location;
import world.owner.Owner;

/**
 * basic builder and gatherer
 * @author Jack
 *
 */
public class Worker extends Unit
{
	public Worker(Owner owner, Location location)
	{
		super(owner, location, "worker", 5, 80, new LightTurret(), 5, 10);
		
		ArrayList<String> u = new ArrayList<String>();
		u.add("hq");
		u.add("barracks");
		BuildTree tree = new BuildTree(u);
		bt = tree;
		movement = 6;
		
		isGatherer = true;
		maxMassHolding = 10;
		isBuilder = true;
	}
}
