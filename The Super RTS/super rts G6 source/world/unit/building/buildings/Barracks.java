package world.unit.building.buildings;

import world.unit.building.Building;
import world.unit.*;
import owner.Owner;
import utilities.Location;
import java.util.ArrayList;

public class Barracks extends Building
{
	public Barracks(Owner owner, Location location)
	{
		super(owner, location, "barracks1", 65, 250, 15, 60);
		ArrayList<String> u = new ArrayList<String>();
		u.add("fighter1");
		u.add("engineer");
		BuildTree tree = new BuildTree(u);
		bt = tree;
		isBuilder = true;
	}
	public boolean is(String type)
	{
		if(type.equalsIgnoreCase("barracks") || type.equalsIgnoreCase(getName()))
		{
			return true;
		}
		return false;
	}
}
