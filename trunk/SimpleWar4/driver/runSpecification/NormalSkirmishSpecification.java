package driver.runSpecification;

//records data needed to start a normal skirmish game

public class NormalSkirmishSpecification
{
	int playerAmount = 2;
	
	public NormalSkirmishSpecification(){}
	public void setPlayerAmount(int setter)
	{
		playerAmount = setter;
	}
	public int getPlayerAmount()
	{
		return playerAmount;
	}
}
