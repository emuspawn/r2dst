package world.unit;

import java.util.ArrayList;
/*
 * given to a building to specify what that building can build
 */

public class BuildTree
{
	ArrayList<String> u = new ArrayList<String>();
	
	public BuildTree(ArrayList<String> buildableUnits)
	{
		u = buildableUnits;
	}
	public boolean canBuildUnit(String name)
	{
		for(int i = 0; i < u.size(); i++)
		{
			if(u.get(i).equalsIgnoreCase(name))
			{
				return true;
			}
		}
		return false;
	}
}