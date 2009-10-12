package ui.controlScheme;

import java.util.ArrayList;
import ui.controlScheme.key.Key;

/**
 * processes user key clicks
 * @author Jack
 *
 * @param <T>
 */
public abstract class ControlScheme <T>
{
	protected ArrayList<Key<T>> k;
	
	public ControlScheme()
	{
		setKeyList();
	}
	protected abstract void setKeyList();
	public void processKeyPress(char c)
	{
		for(int i = k.size()-1; i >= 0; i--)
		{
			k.get(i).processKeyPress(c);
		}
	}
	public void processKeyRelease(char c)
	{
		for(int i = k.size()-1; i >= 0; i--)
		{
			k.get(i).processKeyRelease(c);
		}
	}
	public void performKeyFunctions(T t)
	{
		for(int i = k.size()-1; i >= 0; i--)
		{
			if(k.get(i).isDown())
			{
				k.get(i).performKeyFunction(t);
			}
		}
	}
}
