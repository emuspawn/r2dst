import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


public class TCP_IM_Server extends Thread {
	TCP_Server serv;
	
	public TCP_IM_Server() throws IOException
	{
		serv = new TCP_Server(864);
		
		start();
	}
	
	private String getIPAddressFromSocketAddress(SocketAddress sockAddr)
	{
		String toScan = sockAddr.toString();
		
		if (toScan.indexOf('/') != -1)
			toScan = toScan.substring(toScan.indexOf('/')+1);
		
		Scanner scan = new Scanner(toScan).useDelimiter(":");
		
		if (scan.hasNext())
			return scan.next();
		else
			return toScan;
	}
	
	public void run()
	{
		int lastClientCount = 0;
		
		while (!isInterrupted())
		{
			if (lastClientCount != serv.getClientCount())
			{
				for (int j = 0; j < serv.getClientCount(); j++)
				{	
					serv.writeString(j, "User connected from: ");
					serv.writeString(j, getIPAddressFromSocketAddress(serv.getClientSocketAddress(serv.getClientCount()-1)));
					serv.write(j);
				}
				lastClientCount = serv.getClientCount();
			}
			
			for (int i = 0; i < serv.getClientCount(); i++)
			{
				String str;
				
				//See if there is any data. Read() returns false if there is no data
				if (serv.read(i))
				{
					str = serv.readString(i);
					
					//Read until the buffer is clear
					while (str != null)
					{
						//Send it to all connected clients
						for (int j = 0; j < serv.getClientCount(); j++)
						{
							serv.writeString(j, str);
						}
						
						str = serv.readString(i);
					}
					
					for (int j = 0; j < serv.getClientCount(); j++)
					{
						serv.write(j);
					}
				}
			}
		}
	}
}
