package game;

import java.io.*;
import network.Client;

public class GameClient extends Client
{
	Game g;
	
	public GameClient(Game g, String host, int port)
	{
		super(host, port);
		this.g = g;
	}
	protected void interpretByteArray(DataInputStream bdis, byte id)
	{
		try
		{
			long td = bdis.readLong();
			double tdiff = td/1000.0;
			int length = bdis.readInt();
			KeyAction[] ka = new KeyAction[length];
			for(int i = 0; i < length; i++)
			{
				byte owner = bdis.readByte();
				char c = bdis.readChar();
				boolean pressed = bdis.readBoolean();
				ka[i] = new KeyAction(owner, c, pressed);
			}
			g.updateGame(ka, tdiff);
		}
		catch(IOException e){}
	}
}
