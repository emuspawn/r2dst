package listener;

import inputListener.KeyActionListener;
import driver.MainThread;

public class KeyAction extends KeyActionListener
{
	MainThread mt;
	
	public KeyAction(MainThread mt)
	{
		this.mt = mt;
	}
}
