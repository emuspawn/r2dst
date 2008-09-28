package network.protocol;
import java.awt.*;
import java.io.*;

public class InputProtocol
{
	public InputProtocol(){}
	public Point[] readPointArray(DataInputStream dis)
	{
		try
		{
			Point[] p = new Point[dis.readInt()];
			int x;
			int y;
			for(int i = 0; i < p.length; i++)
			{
				x = dis.readInt();
				if(x != -1)
				{
					y = dis.readInt();
					p[i] = new Point(x, y);
				}
				else
				{
					p[i] = null;
				}
			}
			return p;
		}
		catch(IOException e){}
		return null;
	}
}
