package io;

import java.io.*;
import world.World;
import utilities.Location;
import java.util.ArrayList;
import world.Element;

public class MapWriter
{
	private final static int version = 1;
	public static void writeMap(String mapName, String description, ArrayList<Location> startLocations, World w, DataOutputStream dos) throws IOException
	{
		dos.writeInt(version);
		dos.writeInt(mapName.length());
		dos.writeChars(mapName);
		dos.writeInt(description.length());
		dos.writeChars(description);
		dos.writeInt(w.getWidth());
		dos.writeInt(w.getWidth());
		//start locations
		dos.writeInt(startLocations.size()); //also refers to the max number of players
		for(int i = startLocations.size()-1; i >= 0; i--)
		{
			dos.writeDouble(startLocations.get(i).x);
			dos.writeDouble(startLocations.get(i).y);
		}
		//elements
		ArrayList<Element> e = w.getDynamicMap().getElements();
		dos.writeInt(e.size());
		for(int i = e.size()-1; i >= 0; i--)
		{
			ElementWriter.writeElement(e.get(i), dos);
		}
	}
}
