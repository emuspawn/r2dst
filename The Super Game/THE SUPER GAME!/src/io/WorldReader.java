package io;

import world.World;
import java.io.*;

public class WorldReader
{
	public static void readWorld(World w, String filePath) throws IOException
	{
		DataInputStream dis = new DataInputStream(new FileInputStream(new File(filePath)));
		WorldReader wr = new WorldReader();
		w.setLoadPath(filePath);
		int version = dis.readInt();
		if(version == 1)
		{
			wr.readWorldV1(w, dis);
		}
		else if(version == 2)
		{
			wr.readWorldV2(w, dis);
		}
	}
	private void readWorldV1(World w, DataInputStream dis) throws IOException
	{
		int plength = dis.readInt();
		for(int i = plength-1; i >= 0; i--)
		{
			PermanentReader.readPermanent(w, dis);
		}
		int dlength = dis.readInt();
		for(int i = dlength-1; i >= 0; i--)
		{
			DestructableReader.readDestructable(w, dis);
		}
	}
	private void readWorldV2(World w, DataInputStream dis) throws IOException
	{
		int width = dis.readInt();
		int height = dis.readInt();
		w.getDynamicMap().setSize(width, height);
		int plength = dis.readInt();
		for(int i = plength-1; i >= 0; i--)
		{
			PermanentReader.readPermanent(w, dis);
		}
		int dlength = dis.readInt();
		for(int i = dlength-1; i >= 0; i--)
		{
			DestructableReader.readDestructable(w, dis);
		}
	}
}
