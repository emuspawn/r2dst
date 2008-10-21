package rpgWorld.saver;

import java.io.*;
import tileSystem.*;
import utilities.Map;

public class WorldWriter
{
	public static void saveWorld(String mapName, TileSystem ts, Map mt)
	{
		String userDir = System.getProperty("user.dir");
		File f = new File(userDir+"\\"+mapName+".wrld");
		try
		{
			FileOutputStream fos = new FileOutputStream(f);
			
			WorldWriter ww = new WorldWriter();
			ww.writeToFile(fos, ts, mt);
			
			fos.close();
		}
		catch(IOException e)
		{
			System.out.println("io exception");
		}
	}
	private void writeToFile(FileOutputStream fos, TileSystem ts, Map mt) throws IOException
	{
		DataOutputStream dos = new DataOutputStream(fos);
		
		dos.writeInt(1);
		
		dos.writeInt(mt.getMapWidth());
		dos.writeInt(mt.getMapHeight());
		
		//writes tile types
		TileType[] tt = ts.getTileTypeRegistry().getTileTypes();
		System.out.println("tt.length = "+tt.length);
		dos.writeInt(tt.length);
		for(int i = 0; i < tt.length; i++)
		{
			if(tt[i] != null)
			{
				dos.writeInt(tt[i].getName().length());
				dos.writeChars(tt[i].getName());
				dos.writeInt(tt[i].getType());
				dos.writeInt(tt[i].getColor().getRed());
				dos.writeInt(tt[i].getColor().getGreen());
				dos.writeInt(tt[i].getColor().getBlue());
				dos.writeBoolean(tt[i].getImpassable());
			}
		}
		//writes tiles
		Tile[] t = ts.getTiles();
		System.out.println("number of tiles saved = "+t.length);
		dos.writeInt(t.length);
		for(int i = 0; i < t.length; i++)
		{
			if(t != null)
			{
				dos.writeInt(t[i].getType());
				dos.writeInt(t[i].getLocation().x);
				dos.writeInt(t[i].getLocation().y);
				dos.writeInt(t[i].getWidth());
				dos.writeInt(t[i].getHeight());
			}
		}
	}
}
