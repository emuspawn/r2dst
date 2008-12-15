package engine;

import java.io.IOException;

import drawing.*;

public class MainThread implements Runnable
{
	DrawFrame df;
	
	public MainThread() throws IOException
	{
		df = new DrawFrame();
	}
	
	public static void main(String[] args) throws IOException
	{
		new MainThread();
	}
	
	public void run()
	{
		Thread t = new Thread(df.dc);
		t.start();
		for(;;)
		{
			//put game-related stuff here
			
			
			try
			{
				Thread.sleep(50);
			}
			catch(InterruptedException e){}
		}
	}
}
