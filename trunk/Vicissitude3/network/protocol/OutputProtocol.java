package network.protocol;
import java.awt.*;
import java.io.*;

public class OutputProtocol
{
	public OutputProtocol(){}
	public void writePointArray(DataOutputStream dos, Point[] p)
	{
		try
		{
			dos.writeInt(p.length);
			for(int i = 0; i < p.length; i++)
			{
				if(p[i] != null)
				{
					dos.writeInt(p[i].x);
					dos.writeInt(p[i].y);
				}
				else
				{
					dos.writeInt(-1);
				}
			}
		}
		catch(IOException e){}
	}
}
