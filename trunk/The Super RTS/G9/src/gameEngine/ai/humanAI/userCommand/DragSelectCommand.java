package gameEngine.ai.humanAI.userCommand;

import utilities.Region;
import gameEngine.ai.AI;
import gameEngine.world.unit.Unit;

public class DragSelectCommand extends UserCommand
{
	Region r;
	/**
	 * creates a new drag select command to select units based off a mouse drag
	 * @param sx the starting x position of the mouse
	 * @param sy
	 * @param ex the ending x position of the mouse
	 * @param ey
	 */
	public DragSelectCommand(double sx, double sy, double ex, double ey)
	{
		double x = 0;
		if(sx < ex)
		{
			x = sx;
		}
		else
		{
			x = ex;
		}
		double y = 0;
		if(sy < ey)
		{
			y = sy;
		}
		else
		{
			y = ey;
		}
		double width = Math.abs(ex-sx);
		double height = Math.abs(ey-ex);
		
		
		r = new Region(x, y, width, height);
	}
	public void updateUnit(Unit u, AI ai, boolean override)
	{
		if(r.intersects(u))
		{
			u.setSelected(true);
		}
	}
}
