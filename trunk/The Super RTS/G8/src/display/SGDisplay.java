package display;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import graphics.GLCamera;
import javax.media.opengl.*;
import sgEngine.EngineConstants;
import sgEngine.SGEngine;
import com.sun.opengl.util.j2d.TextRenderer;
import world.Element;
import world.World;
import world.owner.Owner;
import world.shot.Shot;

public class SGDisplay implements GLEventListener
{
	SGEngine sge;
	World w;
	GLCamera c;
	double angle = 2;
	
	TextRenderer textRenderer;
	
	public SGDisplay(SGEngine sge, World w, GLCamera c)
	{
		this.sge = sge;
		this.w = w;
		this.c = c;
		
		Font font = new Font("SansSerif", Font.PLAIN, 12);
        textRenderer = new TextRenderer(font, true, false);
	}
	public void display(GLAutoDrawable d)
	{
		GL gl = d.getGL();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		c.loadMatrices(gl);
		c.updateCamera(gl);
		renderPlane(d, w.getWidth(), w.getDepth());
		ArrayList<Element> e = w.getVisibleElements(c);
		for(int i = e.size()-1; i >= 0; i--)
		{
			if(e.get(i) != null)
			{
				e.get(i).drawElement(gl);
			}
		}
		
		if(EngineConstants.drawShots)
		{
			int shotsDrawn = 0;
			List<Shot> s = w.getShotEngine().getShots();
			try
			{
				Iterator<Shot> si = s.iterator(); //shot iterator
				while(si.hasNext())
				{
					si.next().drawElement(gl);
					shotsDrawn++;
				}
			}
			catch(Exception q){}
			/*if(shotsDrawn != s.size())
			{
				System.out.println("shots drawn: "+shotsDrawn+" / "+s.size());
			}*/
		}
		
		if(!EngineConstants.cameraMode)
		{
			ArrayList<Owner> o = sge.getOwners();
			Iterator<Owner> i = o.iterator();
			while(i.hasNext())
			{
				i.next().getAI().drawUI(d);
			}
			
			drawEngineStats(d);
		}
		
		gl.glPushMatrix();
		gl.glRotated(angle, 0, 1, 0);
		Font font = new Font("SansSerif", Font.BOLD, 32);
        textRenderer = new TextRenderer(font, true, false);
        textRenderer.setColor(0, 0, 255, 255);
		textRenderer.begin3DRendering();
		textRenderer.draw3D("Jack is the best!!", 0, 100, 0, 1);
		textRenderer.end3DRendering();
		angle+=.6;
		font = new Font("SansSerif", Font.PLAIN, 12);
        textRenderer = new TextRenderer(font, true, false);
		gl.glPopMatrix();
	}
	private void renderPlane(GLAutoDrawable d, int width, int height)
	{
		GL gl = d.getGL();
		gl.glPushMatrix();
		gl.glColor3d(0, 255, 0);
		gl.glScaled(1, 1, 1);
		double y = -.5;
		double hwidth = width/2;
		double hheight = height/2;
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(-hwidth, y, -hheight);
		gl.glVertex3d(hwidth, y, -hheight);
		gl.glVertex3d(hwidth, y, hheight);
		gl.glVertex3d(-hwidth, y, hheight);
		gl.glEnd();
		gl.glPopMatrix();
	}
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2){}
	public void init(GLAutoDrawable d)
	{
		GL gl = d.getGL();
		gl.glEnable(GL.GL_DEPTH_TEST);
		//gl.glClearColor(0, 1f, 0, 0);
	}
	public void reshape(GLAutoDrawable d, int arg1, int arg2, int width, int height)
	{
		GL gl = d.getGL();
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustum(-1, 1, -1, 1, 1, 7000);
		c.setSize(width, height);
	}
	private void drawEngineStats(GLAutoDrawable d)
	{
		String units = "total units = "+w.getUnitEngine().getTotalUnits();
		drawText(units, 10, 10, Color.white, d);
	}
	/**
	 * draws text on the screen
	 * @param s the text to be drawn
	 * @param x
	 * @param y
	 * @param c the color of the text
	 * @param d
	 */
	private void drawText(String s, double x, double y, Color c, GLAutoDrawable d)
	{
		textRenderer.beginRendering(d.getWidth(), d.getHeight());
        textRenderer.setColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
        textRenderer.draw(s, (int)x, (int)y);
        textRenderer.endRendering();
	}
}
