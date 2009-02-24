package world.unit.units;

import java.util.ArrayList;
import world.unit.BuildTree;
import world.unit.Unit;
import utilities.Location;
import world.owner.Owner;

public class Worker extends Unit
{
	public Worker(Owner owner, Location location)
	{
		super(owner, location, "worker", 5, 80, 80, 2, 6, 20);
		
		ArrayList<String> u = new ArrayList<String>();
		u.add("hq");
		u.add("barracks");
		BuildTree tree = new BuildTree(u);
		bt = tree;
		
		isGatherer = true;
		maxMassHolding = 10;
		isBuilder = true;
	}
	public boolean is(String type)
	{
		if(type.equalsIgnoreCase("worker") || type.equalsIgnoreCase(getName()))
		{
			return true;
		}
		return false;
	}
}
