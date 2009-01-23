package world.action;

public abstract class Action
{
	String name;
	
	public Action(String name)
	{
		this.name = name;
	}
	public abstract void performAction();
}
