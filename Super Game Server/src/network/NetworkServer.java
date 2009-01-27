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
	
	public static void main(String[] args)
	{
		int port = 1164;
		try {
			new NetworkServer(port, new World(true));
			System.out.println("The network server is up and running on port "+port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public NetworkServer(int port, World wld) throws IOException
	{
		serv = new TCP_Server(port);
		wrld = wld;
		start();
	}
	
	public void run()
	{
		while (serv.getClientCount() == 0);
		System.out.println("Got a client");
		
		while (!isInterrupted())
		{
			for (int i = 0; i < serv.getClientCount(); i++)
			{
				NetworkPacket pack;
				try {
					pack = (NetworkPacket)serv.readObject(i);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					continue;
				}

				if (pack != null)
				{
					//System.out.println("Got packet with type: " +pack.type);
					
					switch (pack.type)
					{
					case 0:
						wrld.updateUserScreenDimensions((Integer)pack.data[0], (Integer)pack.data[1], (Integer)pack.data[2]);
						break;
						
					case 1:
						wrld.interpretUserAction((Byte)pack.data[0], (Byte)pack.data[1]);
						break;
					
					case 2:
						ArrayList<Element> els = wrld.getVisibleElements((Integer)pack.data[0]);
						
						for (int k = 0; k < els.size(); k++)
						{
							NetworkPacket newPack = new NetworkPacket();
							newPack.type = 5;
							newPack.data = new Object[] {els.get(k).getElementType(), els.get(k).getName(), els.get(k).getLocation(), els.get(k).width, els.get(k).height};
							serv.writeObject(i, newPack);
							serv.flush(i);
						}
						
						NetworkPacket compPack = new NetworkPacket();
						
						compPack.type = -1;
						compPack.data = null;
						
						serv.writeObject(i, compPack);
						serv.flush(i);
						
						break;
						
					case 3:
						Camera userCam = wrld.getUserCamera((Integer)pack.data[0]);
						serv.writeObject(i, new Integer[] {userCam.getWidth(), userCam.getWidth(), userCam.getxover(), userCam.getyover()});
						serv.flush(i);
						break;
						
					case 4:
						serv.writeObject(i, (Integer)wrld.formConnection((String)pack.data[0]));
						serv.flush(i);
						break;
					}
				}
			}
		}
	}
}
