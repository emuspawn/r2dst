package world.unit.building.buildings;

import world.unit.building.Building;
import world.unit.*;
import owner.Owner;
import utilities.Location;
import java.util.ArrayList;

public class Factory extends Building
{
	public Factory(Owner owner, Location location)
	{
		super(owner, location, "factory", 80, 200, 30, 80);
		ArrayList<String> u = new ArrayList<String>();
		//u.add("fighter");
		BuildTree tree = new BuildTree(u);
		bt = tree;
		isBuilder = true;
	}
}