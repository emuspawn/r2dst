package mapEditor.loader;

import java.io.*;
import mapEditor.tile.*;
import mapEditor.*;
import utilities.StringPartitioner;

public class MapReadifyer
{
	MapEditor me;
	
	public MapReadifyer(MapEditor me)
	{
		this.me = me;
	}
	public void readMap()
	{
		//System.out.println("read map called");
		String userDir = System.getProperty("user.dir");
		File f = new File(userDir+"\\mapEditor\\mapLayout\\"+me.getMapName()+".txt");
		try
		{
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			readDataFromFile(br);
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("io exception");
		}
	}
	private void readDataFromFile(BufferedReader br) throws IOException
	{
		int version = Integer.parseInt(br.readLine());
		//System.out.println("version = "+version);
		if(version == 1)
		{
			readFromVersion1(br);
		}
		else if(version == 2)
		{
			readFromVersion2(br);
		}
	}
	private void readFromVersion2(BufferedReader br) throws IOException
	{
		int length = Integer.parseInt(br.readLine());
		me.setMapWidth(Integer.parseInt(br.readLine()));
		me.setMapHeight(Integer.parseInt(br.readLine()));
		//System.out.println("length = "+length);
		Tile[] t = new Tile[length];
		StringPartitioner sp = new StringPartitioner();
		String line;
		String[] td; //tile data
		int type;
		int x;
		int y;
		int width;
		int height;
		for(int i = 0; i < t.length; i++)
		{
			line = br.readLine();
			if(line.equalsIgnoreCase("null"))
			{
				t[i] = null;
			}
			else
			{
				td = sp.breakString(line, ' ');
				type = Integer.parseInt(td[0]);
				x = Integer.parseInt(td[1]);
				y = Integer.parseInt(td[2]);
				width = Integer.parseInt(td[3]);
				height = Integer.parseInt(td[4]);
				me.addTile(new Tile(type, x, y, width, height));
			}
		}
	}
	private void readFromVersion1(BufferedReader br) throws IOException
	{
		int length = Integer.parseInt(br.readLine());
		//System.out.println("length = "+length);
		Tile[] t = new Tile[length];
		StringPartitioner sp = new StringPartitioner();
		String line;
		String[] td; //tile data
		int type;
		int x;
		int y;
		int width;
		int height;
		for(int i = 0; i < t.length; i++)
		{
			line = br.readLine();
			if(line.equalsIgnoreCase("null"))
			{
				t[i] = null;
			}
			else
			{
				td = sp.breakString(line, ' ');
				type = Integer.parseInt(td[0]);
				x = Integer.parseInt(td[1]);
				y = Integer.parseInt(td[2]);
				width = Integer.parseInt(td[3]);
				height = Integer.parseInt(td[4]);
				me.addTile(new Tile(type, x, y, width, height));
			}
		}
	}
}
