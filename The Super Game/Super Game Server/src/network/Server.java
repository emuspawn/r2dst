package network;

import java.io.IOException;
import world.World;

public class Server {
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
}
