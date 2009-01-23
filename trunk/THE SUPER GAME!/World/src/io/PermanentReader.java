package io;

import world.World;
import permanents.factories.TerrainFactory;
import java.io.*;
import utilities.Location;
import world.permanent.Permanent;

public class PermanentReader
{
	public static void readPermanent(World w, DataInputStream dis) throws IOException
	{
		int slength = dis.readInt();
		String name = new String();
		for(int i = slength-1; i >= 0; i--)
		{
			name+=dis.readChar();
		}
		double x = dis.readDouble();
		double y = dis.readDouble();
		Permanent p = TerrainFactory.createTerrain(new Location(x, y), name);
		w.addPermanent(p);
	}
}
