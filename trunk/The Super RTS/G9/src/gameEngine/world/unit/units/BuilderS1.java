package gameEngine.world.unit.units;

import gameEngine.world.owner.Owner;
import gameEngine.world.unit.Unit;

/**
 * a basic stage 1 builder
 * 
 * draw with a picture of a hammer
 * @author Jack
 *
 */
public class BuilderS1 extends Unit
{

	public BuilderS1(Owner o, double x, double y)
	{
		super("builder s1", o, x, y, 15, 11, 100, null);
	}
	
}
