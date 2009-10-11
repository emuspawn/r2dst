package gameEngine.ai.computerAI;

import gameEngine.ai.AI;
import gameEngine.world.World;
import gameEngine.world.owner.Owner;
import javax.media.opengl.GL;

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
	public abstract void performAIFunctions();
	public void drawUI(GL gl){}
	public void interpretMouseClick(double x, double y, boolean pressed, boolean rightClick){}
	public void interpretMouseMove(double x, double y, boolean dragged, boolean rightClick){}
}
