import java.io.IOException;


public class TCP_IM_Server extends Thread {
	TCP_Server serv;
	
	public TCP_IM_Server() throws IOException
	{
		serv = new TCP_Server(864);
		
		start();
	}
	
	public void run()
	{
		while (!isInterrupted())
		{
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
							serv.write(j);
						}
						
						str = serv.readString(i);
					}
				}
			}
		}
	}
}
