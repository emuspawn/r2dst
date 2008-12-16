
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
				//This will contain the message part that is currently being processed
				String str = cli.readString();
				
				//Continue reading until we have cleared the buffer
				while (str != null)
				{
					System.out.print(str);
					
					str = cli.readString();
				}
				
				System.out.println();
			}
		}
	}
}
