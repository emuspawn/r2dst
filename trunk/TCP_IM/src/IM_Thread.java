
public class IM_Thread extends Thread {
	private TCP_Client cli;
	public IM_Thread(TCP_Client cl)
	{
		cli = cl;
		start();
	}
	
	public void run()
	{
		while (!isInterrupted())
		{
			//Check if we have data
			if (cli.read())
			{
				//The first string we send is the name
				String name = cli.readString();
				
				//This will contain the entire message
				String fullMessage = "";
				
				//This will contain the message part that is currently being processed
				//We'll go ahead and fill the string with the first part of the message
				String str = cli.readString();
				
				//Continue reading until we have cleared the buffer
				while (str != null)
				{
					fullMessage += str;
					
					str = cli.readString();
				}
				
				//Print the name and the complete message
				System.out.println(name+": "+fullMessage);
			}
		}
	}
}
