package ai.computerAI;

import javax.media.opengl.GLAutoDrawable;

import sgEngine.userAction.MouseClick;
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

	public ComputerAI(Owner o, World w)
	{
		super(o, w);
	}
	public void interpretMouseClick(MouseClick ma){}
	public void drawUI(GLAutoDrawable d){}
	public abstract void performAIFunctions();
}
