package game;

import javax.media.opengl.GL;

import ui.display.Displayable;

public class Display implements Displayable
{
	Game g;
	
	public Display(Game g)
	{
		this.g = g;
	}
	public void display(GL gl, int viewWidth, int viewHeight)
	{
		gl.glClearColor(1, 1, 1, 1);
		Unit[] u = g.getUnits();
		gl.glColor3d(1, 0, 0);
		for(int i = 0; i < u.length; i++)
		{
			u[i].fillRegion(gl);
		}
	}
}
