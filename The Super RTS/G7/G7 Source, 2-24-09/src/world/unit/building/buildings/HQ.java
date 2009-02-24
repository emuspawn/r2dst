package world.unit.building.buildings;

import java.util.ArrayList;
import world.unit.BuildTree;
import world.unit.building.Building;
import world.owner.Owner;
import utilities.Location;

public class HQ extends Building
{
	public HQ(Owner owner, Location location)
	{
		super(owner, location, "hq", 75, 220, 22, 40);
		ArrayList<String> u = new ArrayList<String>();
		u.add("worker");
		BuildTree tree = new BuildTree(u);
		bt = tree;
		isBuilder = true;
		populationAugment = 10;
		populationValue = 0;
	}
	public boolean is(String type)
	{
		if(type.equalsIgnoreCase("hq") || type.equalsIgnoreCase(getName()))
		{
			return true;
		}
		return false;
	}
}
