package connection;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import world.Element;
import java.util.ArrayList;
import graphics.Camera;
import world.World;

public class DirectConnection extends Connection
{
	World w;
	KeyMap km;
	String userName;
	
	public DirectConnection(World w, String userName)
	{
		this.w = w;
		this.userName = userName;
		try
		{
			File f = new File(System.getProperty("user.dir")+"\\keyMapV1.km");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			km = new KeyMap(dis);
		}
		catch(IOException e)
		{
			System.out.println("io exception");
		}
		w.formConnection(this);
	}
	public String getUserName()
	{
		return userName;
	}
	public void relayUserAction(char c)
	{
		w.interpretUserAction(km.getUserActionByte(c), Byte.parseByte(index+""));
	}
	public ArrayList<Element> getVisibleElements()
	{
		return w.getVisibleElements(index);
	}
	public Camera getCamera()
	{
		return w.getUserCamera(index);
	}
	public void setIndex(int setter)
	{
		index = setter;
	}
	public void sendScreenDimensions(int width, int height)
	{
		w.updateUserScreenDimensions(index, width, height);
	}
}
