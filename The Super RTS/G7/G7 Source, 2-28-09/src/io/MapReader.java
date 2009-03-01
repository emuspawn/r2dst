package io;

import world.World;
import utilities.Location;
import java.io.*;
import world.terrain.Terrain;
import java.util.ArrayList;
import factory.*;
import world.resource.Resource;

/**
 * reads world files
 * @author Jack
 *
 */
public final class MapReader
{
	/**
	 * reads the world
	 * @param w the world that the read map is to be stored to
	 * @param dis the input stream respresenting that file that is being read
	 * @throws IOException
	 */
	public static void readMap(World w, DataInputStream dis) throws IOException
	{
		//System.out.println("map name = "+readMapName(dis));
		readMapDescription(dis);
		
		int mapWidth = dis.readInt();
		int mapHeight = dis.readInt();
		w.setSize(mapWidth, mapHeight);
		
		Location[] startLocations = readStartLocations(dis);
		w.setStartLocations(startLocations);
		
		ArrayList<Terrain> t = new ArrayList<Terrain>();
		ArrayList<Resource> r = new ArrayList<Resource>();
		int length = dis.readInt();
		int type;
		String name;
		double x;
		double y;
		int width;
		int height;
		for(int i = length-1; i >= 0; i--)
		{
			type = dis.readInt();
			name = readString(dis);
			x = dis.readDouble();
			y = dis.readDouble();
			width = dis.readInt();
			height = dis.readInt();
			if(type == IOConstants.terrain)
			{
				t.add(TerrainFactory.makeTerrain(name, new Location(x, y), width, height));
			}
			else if(type == IOConstants.resource)
			{
				r.add(ResourceFactory.makeResource(name, new Location(x, y)));
			}
		}
		for(int i = t.size()-1; i >= 0; i--)
		{
			w.registerElement(t.get(i));
		}
		for(int i = r.size()-1; i >= 0; i--)
		{
			w.registerElement(r.get(i));
		}
	}
	private static Location[] readStartLocations(DataInputStream dis) throws IOException
	{
		int length = dis.readInt();
		Location[] l = new Location[length];
		double x;
		double y;
		for(int i = length-1; i >= 0; i--)
		{
			x = dis.readDouble();
			y = dis.readDouble();
			l[i] = new Location(x, y);
		}
		return l;
	}
	private static String readString(DataInputStream dis) throws IOException
	{
		int length = dis.readInt();
		String s = new String();
		for(int i = length-1; i >= 0; i--)
		{
			s += dis.readChar();
		}
		return s;
	}
	/**
	 * reads the world name
	 * @param dis the stream representing the world file
	 * @return returns that world name
	 * @throws IOException
	 */
	public static String readMapName(DataInputStream dis) throws IOException
	{
		dis.readInt();
		return readString(dis);
	}
	/**
	 * reads the world description
	 * @param dis the stream representing the world file
	 * @return returns the world description
	 * @throws IOException
	 */
	public static String readMapDescription(DataInputStream dis) throws IOException
	{
		dis.readInt();
		readString(dis);
		return readString(dis);
	}
	
}
