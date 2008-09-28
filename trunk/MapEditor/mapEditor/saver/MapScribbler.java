package mapEditor.saver;

import java.io.File;
import java.io.IOException;
import mapEditor.*;
import javax.imageio.*;
import java.awt.image.*;

/*
 * saves a picture of a map file
 */

public class MapScribbler
{
	MapEditor me;
	
	public MapScribbler(MapEditor me)
	{
		this.me = me;
	}
	public void saveMapPicture()
	{
		String userDir = System.getProperty("user.dir");
		File f = new File(userDir+"\\mapEditor\\mapLayoutPic\\"+me.getMapName()+".PNG");
		try
		{
			writeDataToFile(f);
		}
		catch(IOException e)
		{
			System.out.println("io exception");
		}
	}
	private void writeDataToFile(File f) throws IOException
	{
		BufferedImage bi = new BufferedImage(me.getMapWidth(), me.getMapHeight(), BufferedImage.TYPE_INT_RGB);
		me.getMapEditorDrawer().drawMap(bi.getGraphics(), true);
		ImageIO.write(bi, "PNG", f);
	}
}