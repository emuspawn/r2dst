public class TCP_Lock {
	private boolean lockState;
	
	public TCP_Lock(boolean initialState)
	{
		lockState = initialState;
	}
	
	public void waitForRelease()
	{
		while(lockState);
	}
	
	public void release()
	{
		if (lockState == false)
		{
			//System.out.println("Double released lock!!!");
			//throw new IllegalStateException();
		}
		lockState = false;		
	}
	
	public void acquire()
	{
		waitForRelease();
		lockState = true;
	}
}
