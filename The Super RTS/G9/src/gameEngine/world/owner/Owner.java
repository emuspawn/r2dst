package gameEngine.world.owner;

/**
 * stores the owner dependent stats such as resources and unit count
 * @author Jack
 *
 */
public class Owner
{
	String name;
	double resources = 0;
	int unitCount = 0;
	double[] c; //owner color
	
	public Owner(String name, double[] c)
	{
		this.name = name;
		this.c = c;
	}
	public double[] getColor()
	{
		return c;
	}
}
