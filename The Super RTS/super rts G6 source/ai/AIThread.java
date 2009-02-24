package ai;

import owner.Owner;

public class AIThread implements Runnable
{
	AI ai;
	Owner o;
	
	public AIThread(AI ai, Owner o)
	{
		this.ai = ai;
		this.o = o;
		new Thread(this).start();
	}
	public void run()
	{
		for(;;)
		{
			try
			{
				ai.performAIFunctions(o);
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
