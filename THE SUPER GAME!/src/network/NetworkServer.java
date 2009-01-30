package network;

import graphics.Camera;

import java.io.IOException;
import java.util.ArrayList;


import world.Element;
import world.World;
import TCPv2.TCP_Server;

public class NetworkServer extends Thread {
	TCP_Server serv;
	World wrld;
	
	public NetworkServer(int port, World wld) throws IOException
	{
		serv = new TCP_Server(port);
		wrld = wld;
		start();
	}
	
	private Integer getIntResponse(int client)
	{
		Integer read = null;
		
		while ((read = serv.readInt(client)) == null) Thread.yield();
		
		return read;
	}
	
	private Byte getByteResponse(int client)
	{
		Byte read = null;
		
		while ((read = serv.readByte(client)) == null) Thread.yield();
		
		return read;
	}
	
	private String getStringResponse(int client)
	{
		String read = null;
			
		while ((read = serv.readString(client)) == null) Thread.yield();
			
		return read;
	}
	
	public void run()
	{
		int lastClientCount = 0;
		
		while (serv.getClientCount() == 0) Thread.yield();
		
		while (!isInterrupted())
		{
			int diff;
			if ((diff = serv.getClientCount() - lastClientCount) > 0)
			{
				System.out.println(diff + " new client(s) connected");
				lastClientCount += diff;
			}
			
			for (int i = 0; i < serv.getClientCount(); i++)
			{
				Integer type = serv.readInt(i);
					
				if (type != null)
				{
					//System.out.println("Got packet with type: " +pack.type);
					
					switch (type)
					{
					case 0:
						wrld.updateUserScreenDimensions(getIntResponse(i), getIntResponse(i), getIntResponse(i));
						break;
						
					case 1:
						wrld.interpretUserAction(getByteResponse(i), getByteResponse(i));
						break;
					
					case 2:
						ArrayList<Element> els = wrld.getVisibleElements(getIntResponse(i));

						serv.writeInt(i, els.size());
						for (int k = 0; k < els.size(); k++)
						{
							serv.writeInt(i, els.get(k).getElementType());
							serv.writeString(i, els.get(k).getName());
							serv.writeDouble(i, els.get(k).getLocation().x);
							serv.writeDouble(i, els.get(k).getLocation().y);
							serv.writeInt(i, els.get(k).width);
							serv.writeInt(i, els.get(k).height);
						}
						
						serv.flush(i);
						
						break;
						
					case 3:
						int userIndex = getIntResponse(i);
						Camera userCam = wrld.getUserCamera(userIndex);
						serv.writeInt(i, userCam.getWidth());
						serv.writeInt(i, userCam.getHeight());
						serv.writeInt(i, (int)wrld.getUser(userIndex).getUnit().getLocation().x);
						serv.writeInt(i, (int)wrld.getUser(userIndex).getUnit().getLocation().y);
						serv.flush(i);
						break;
						
					case 4:
						serv.writeInt(i, (Integer)wrld.formConnection(getStringResponse(i)));
						serv.flush(i);
						break;
					}
				}
			}
		}
	}
}
