package rpgWorld.loader;

import java.io.*;
import tileSystem.*;
import java.awt.Color;
import utilities.Map;

public class WorldReader
{
	public static void loadWorld(String fileName, TileSystem ts, Map m)
	{
		String userDir = System.getProperty("user.dir");
		File f = new File(userDir+"\\"+fileName);
		try
		{
			FileInputStream fis = new FileInputStream(f);
			
			WorldReader wr = new WorldReader();
			wr.readFromFile(fis, ts, m);
			
			fis.close();
		}
		catch(IOException e)
		{
			System.out.println("io exception");
		}
	}
	private void readFromFile(FileInputStream fis, TileSystem ts, Map m) throws IOException
	{
		DataInputStream dis = new DataInputStream(fis);
		int version = dis.readInt();
		if(version == 1)
		{
			readVersion1(fis, ts, m);
		}
	}
	private void readVersion1(FileInputStream fis, TileSystem ts, Map m) throws IOException
	{
		DataInputStream dis = new DataInputStream(fis);
		int mapWidth = dis.readInt();
		int mapHeight = dis.readInt();
		ts.setupTileSelectionMatrix(mapWidth, mapHeight);
		TileType[] tt = new TileType[dis.readInt()];
		for(int i = 0; i < tt.length; i++)
		{
			int nl = dis.readInt(); //name length
			String name = new String();
			for(int a = 0; a < nl; a++)
			{
				name+=dis.readChar();
			}
			int type = dis.readInt();
			int red = dis.readInt();
			int green = dis.readInt();
			int blue = dis.readInt();
			boolean impassable = dis.readBoolean();
			tt[i] = new TileType(type, name, new Color(red, green, blue), impassable);
		}
		Tile[] t = new Tile[dis.readInt()];
		for(int i = 0; i < t.length; i++)
		{
			int type = dis.readInt();
			int x = dis.readInt();
			int y = dis.readInt();
			int width = dis.readInt();
			int height = dis.readInt();
			t[i] = new Tile(type, x, y, width, height);
		}
		
		
		//registers everything loaded from the file
		m.setMapWidth(mapWidth);
		m.setMapHeight(mapHeight);
		ts.resetTileRegistry();
		ts.registerTileTypes(tt);
		ts.registerTiles(t);
	}
}
