package world.pathFinder;

import world.unit.*;

public class PathFindingEngine implements Runnable
{
	UnitEngine ue;
	
	public PathFindingEngine(UnitEngine ue)
	{
		this.ue = ue;
		
		Thread runner = new Thread(this);
		runner.start();
	}
	public void run()
	{
		for(;;)
		{
			ue.findUnitPaths();
			try
			{
				Thread.sleep(200);
			}
			catch(InterruptedException e){}
		}
	}
}
