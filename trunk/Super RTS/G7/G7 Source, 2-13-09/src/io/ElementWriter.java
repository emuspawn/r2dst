package io;

import java.io.*;
import world.Element;
import world.terrain.Terrain;
import world.resource.Resource;

public class ElementWriter
{
	public static void writeElement(Element e, DataOutputStream dos) throws IOException
	{
		if(e.getClass().getSuperclass() == Terrain.class)
		{
			dos.writeInt(IOConstants.terrain);
		}
		else if(e.getClass().getSuperclass() == Resource.class)
		{
			dos.writeInt(IOConstants.resource);
		}
		dos.writeInt(e.getName().length());
		dos.writeChars(e.getName());
		dos.writeDouble(e.getLocation().x);
		dos.writeDouble(e.getLocation().y);
		dos.writeInt(e.getWidth());
		dos.writeInt(e.getHeight());
	}
}
