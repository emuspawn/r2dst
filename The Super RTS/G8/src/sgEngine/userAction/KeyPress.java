package sgEngine.userAction;

import graphics.GLCamera;
import world.owner.Owner;

public class KeyPress extends KeyAction
{
	public KeyPress(char c, Owner owner, GLCamera camera, int runTime)
	{
		super("key press", c, owner, camera, runTime);
	}
}
