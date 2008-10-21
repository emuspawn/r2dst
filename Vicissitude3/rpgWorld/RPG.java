package rpgWorld;

import controller.RPGController;
import graphics.Camera;

/*
 * display
 */

public class RPG implements RPGController
{
	boolean windowed = true;
	Camera c;
	
	//single player
	RPGWorld rpgw;
	RPGClientWorld rpgcw;
	
	public RPG(boolean singlePlayer)
	{
		c = new Camera();
		
		if(singlePlayer)
		{
			setupSinglePlayerRPG();
		}
	}
	public RPGClientWorld getRPGClientWorld()
	{
		return rpgcw;
	}
	private void setupSinglePlayerRPG()
	{
		rpgw = new RPGWorld();
		rpgcw = new RPGClientWorld(rpgw, this);
	}
	public static void main(String[] args)
	{
		new RPG(true);
	}
	public void setWindowed(boolean setter)
	{
		windowed = setter;
	}
	public Camera getCamera()
	{
		return c;
	}
}
