package pathFinder;

import utilities.Location;

public class Path
{
	int index = 0; //the spot in the path the unit is currently going to
	Location[] path = new Location[1];
	int addingIndex = 0;
	
	public Path(){}
	public void addLocationToPath(Location l)
	{
		if(addingIndex == path.length)
		{
			enlargePathArray();
		}
		path[addingIndex] = l;
		addingIndex++;
	}
	public Location[] getFullPath()
	{
		//for debuging purposes, used to draw path in GameDrawer
		return path;
	}
	public void setFullPath(Location[] l)
	{
		path = l;
	}
	public Location getNextLocation()
	{
		//System.out.println("index = "+index);
		index++;
		if(index-1 == path.length)
		{
			return null;
		}
		return path[index-1];
	}
	private void enlargePathArray()
	{
		Location[] temp = new Location[path.length+1];
		for(int i = 0; i < path.length; i++)
		{
			temp[i] = path[i];
		}
		path = temp;
	}
}
