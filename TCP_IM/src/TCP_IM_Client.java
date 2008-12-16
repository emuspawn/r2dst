import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class TCP_IM_Client extends Thread {
	private TCP_Client cli;
	private IM_Thread recvThread;
	private String name;
	
	public TCP_IM_Client(String userName, InetAddress addr) throws IOException
	{
		name = userName;
		
		cli = new TCP_Client(addr, 864);
		
		recvThread = new IM_Thread(cli);
		start();
	}
	
	public void run()
	{
		Scanner scan = new Scanner(System.in).useDelimiter("\n");
		
		System.out.println("--------------- Chat started ------------------");
		
		while (!isInterrupted())
		{
			if (scan.hasNext())
			{
				String message = scan.next().trim();
				
				if (message.equalsIgnoreCase(".quit"))
					break;
				
				for (int i = 0; i < message.length(); i++)
				{
					cli.writeString(""+message.charAt(i));
				}
				
				//Call write() to do the actual send
				cli.write();
			}
		}
		
		System.out.println("---------------- Chat ended -------------------");
		
		//Cleanup before dying
		disconnect();
	}
	
	private void disconnect()
	{
		recvThread.interrupt();
		
		//Wait for the thread to die before we close the TCP_Client
		while(recvThread.isAlive());
		
		//Close the TCP_Client
		cli.close();
	}
}
