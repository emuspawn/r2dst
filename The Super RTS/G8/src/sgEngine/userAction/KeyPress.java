package sgEngine.userAction;

import java.awt.event.KeyEvent;

import graphics.GLCamera;
import world.owner.Owner;

public class KeyPress extends KeyAction
{
	public KeyPress(KeyEvent e, Owner owner, GLCamera camera, int runTime)
	{
		super("key press", e, owner, camera, runTime);
	}
}
