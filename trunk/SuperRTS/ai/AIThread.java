package ai;

public class AIThread implements Runnable
{
	AI ai;
	
	public AIThread(AI ai)
	{
		this.ai = ai;
		new Thread(this).start();
	}
	public void run()
	{
		for(;;)
		{
			try
			{
				ai.performAIFunctions();
			}
			catch(Exception e)
			{
				System.out.println("exception caught in AIThread, continuing anyway");
				System.out.println("probably related to out of sync threads, nothing to worry about :)");
				e.printStackTrace();
			}
			try
			{
				Thread.sleep(50);
			}
			catch(InterruptedException e){}
		}
	}
}
