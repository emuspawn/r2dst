package sgEngine.userAction;

import graphics.GLCamera;
import world.owner.Owner;

public class KeyRelease extends KeyAction
{
	public KeyRelease(char c, Owner owner, GLCamera camera, int runTime)
	{
		super("key release", c, owner, camera, runTime);
	}
}
