package network;

import graphics.Camera;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import world.Element;
import world.World;
import TCPv3.TCP_Server;

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
					//System.out.println("Got packet with type: " +type);
					
					switch (type)
					{
					case 0:
						wrld.updateUserScreenDimensions(getIntResponse(i), getIntResponse(i), getIntResponse(i));
						break;
						
					case 1:
						wrld.interpretUserAction(getIntResponse(i).byteValue(), getIntResponse(i).byteValue());
						break;
					
					case 2:
						ArrayList<Element> els = wrld.getVisibleElements(getIntResponse(i));

						serv.writeInt(i, els.size());
						for (int k = 0; k < els.size(); k++)
						{
							serv.writeInt(i, els.get(k).isImpassable() ? 1 : 0);
							serv.writeInt(i, (int)els.get(k).getLocation().x);
							serv.writeInt(i, (int)els.get(k).getLocation().y);
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
						serv.writeInt(i, (Integer)wrld.formConnection("test"));
						serv.flush(i);
						
						while (getIntResponse(i) != 10);
						
						serv.writeInt(i, 10);
						serv.writeFile(i, new File(wrld.getLoadPath()));
						
						serv.flush(i);
						break;
					}
				}
			}
		}
	}
}
