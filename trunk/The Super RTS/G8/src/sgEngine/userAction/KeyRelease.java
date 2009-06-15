package sgEngine.userAction;

import java.awt.event.KeyEvent;

import graphics.GLCamera;
import world.owner.Owner;

public class KeyRelease extends KeyAction
{
	public KeyRelease(KeyEvent e, Owner owner, GLCamera camera, int runTime)
	{
		super("key release", e, owner, camera, runTime);
	}
}
