package connection;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import utilities.Location;
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
		setIndex(w.formConnection(userName));
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
		ArrayList<Element> el = w.getElements();
		Camera cam = w.getUserCamera(index);
		
		for (int i = 0; i < el.size(); i++)
		{
			if (!isInMap(cam, el.get(i).getLocation()))
				el.remove(i);
		}
		
		return el;
	}
	private boolean isInMap(Camera cam, Location loc)
	{
		if (loc.x > (cam.getxover() + cam.getWidth()) || loc.x < cam.getxover())
			return false;
		
		if (loc.y > (cam.getyover() + cam.getHeight()) || loc.y < cam.getyover())
			return false;
		
		return true;
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
