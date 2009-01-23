package connection;

public class Account
{
	private String name;
	private int level;
	private int gold;
	
	public Account(String name, int level, int gold)
	{
		this.name = name;
		this.level = level;
		this.gold = gold;
	}
	public String getName()
	{
		return name;
	}
	public int getLevel()
	{
		return level;
	}
	public int getGold()
	{
		return gold;
	}
}
