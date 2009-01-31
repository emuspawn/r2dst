package network;

public class Lock {
	boolean lockState = false;
	
	public void waitForLock()
	{
		while (lockState);
	}
	
	public void acquireLock()
	{
		waitForLock();
		lockState = true;
	}
	
	public void releaseLock()
	{
		lockState = false;
	}
}
