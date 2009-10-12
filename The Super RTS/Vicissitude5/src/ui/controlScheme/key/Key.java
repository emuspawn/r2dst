package ui.controlScheme.key;

//represents a key on the keyboard

public abstract class Key <T>
{
	private char c;
	private boolean down = false;
	
	public Key(char c)
	{
		this.c = c;
	}
	public void processKeyPress(char pressed)
	{
		if(pressed == c)
		{
			down = true;
		}
	}
	public void processKeyRelease(char released)
	{
		if(released == c)
		{
			down = false;
		}
	}
	public abstract void performKeyFunction(T t);
	public boolean isDown()
	{
		return down;
	}
}
