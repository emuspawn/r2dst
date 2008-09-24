package editor.mapEditor.saver;

import java.io.*;
import editor.mapEditor.*;
import editor.mapEditor.tile.*;

/*
 * saves the .txt equavalent of the map for editing purposes only, a picture will be used
 * for in game display
 */

public class MapScribe
{
	MapEditor me;
	int version = 1;
	
	public MapScribe(MapEditor me)
	{
		this.me = me;
	}
	public void saveMap()
	{
		String userDir = System.getProperty("user.dir");
		File f = new File(userDir+"\\editor\\mapEditor\\mapLayout\\"+me.getMapName()+".txt");
		try
		{
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			writeDataToFile(bw);
			bw.close();
		}
		catch(IOException e)
		{
			System.out.println("io exception");
		}
	}
	private void writeDataToFile(BufferedWriter bw) throws IOException
	{
		Tile[] t = me.getTiles();
		bw.write(""+version);
		bw.newLine();
		bw.write(""+t.length);
		bw.newLine();
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] != null)
			{
				bw.write(t[i].toString());
			}
			else
			{
				bw.write("null");
			}
			bw.newLine();
		}
	}
}
