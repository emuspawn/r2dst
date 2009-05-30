package ai.computerAI;

import javax.media.opengl.GLAutoDrawable;

import sgEngine.SGEngine;
import sgEngine.userAction.KeyPress;
import sgEngine.userAction.KeyRelease;
import sgEngine.userAction.MouseAction;
import world.World;
import world.owner.Owner;
import ai.AI;

/**
 * basic low level AI for a computer
 * @author Jack
 *
 */
public abstract class ComputerAI extends AI
{

	public ComputerAI(Owner o, World w, SGEngine sge)
	{
		super(o, w, sge);
	}
	public abstract void performAIFunctions();
	public void drawUI(GLAutoDrawable d){}
	public void interpretMouseClick(MouseAction ma){}
	public void interpretMousePress(MouseAction mc){}
	public void interpretMouseRelease(MouseAction mc){}
	public void interpretKeyPress(KeyPress kp){}
	public void interpretKeyRelease(KeyRelease kr){}
}
