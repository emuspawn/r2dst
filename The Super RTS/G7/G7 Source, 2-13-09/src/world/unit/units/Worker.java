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
		super(owner, location, "worker1", 5, 80, 80, 2, 6, 20);
		
		ArrayList<String> u = new ArrayList<String>();
		u.add("hq1");
		u.add("barracks1");
		BuildTree tree = new BuildTree(u);
		bt = tree;
		
		isGatherer = true;
		gatheringRange = 15;
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
