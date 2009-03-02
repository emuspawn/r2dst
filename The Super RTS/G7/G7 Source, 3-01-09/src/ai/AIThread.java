package ai;

import world.World;

public class AIThread implements Runnable
{
	AI ai;
	boolean stop = false;
	//int runTimes = 0;
	
	public AIThread(AI ai, World w)
	{
		this.ai = ai;
		ai.setWorld(w);
	}
	public void start()
	{
		new Thread(this).start();
	}
	public void stop()
	{
		stop = true;
	}
	public void run()
	{
		for(;;)
		{
			if(!stop)
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
				//runTimes++;
				//System.out.println(ai.o.getName()+" "+runTimes);
			}
		}
	}
}
