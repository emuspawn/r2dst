package editor.modelEditor;

import graphics.GLCamera;
import javax.media.opengl.*;

public class ModelDisplay implements GLEventListener
{
	GLCamera c;
	Model m;
	
	public ModelDisplay(GLCamera c, Model m)
	{
		this.c = c;
		this.m = m;
	}
	public void display(GLAutoDrawable d)
	{
		GL gl = d.getGL();
		c.loadMatrices(gl);
		c.updateCamera(gl);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		gl.glColor3d(255, 0, 0);
		//new Prism(new Location(0, 0, 0), .3, .3, .3).drawPrism(gl);
		//new Prism(new Location(0, 0, 0), .2, 50, .2).drawPrism(gl);
		if(ModelEditorConstants.drawGrid)
		{
			drawGrid(gl);
		}
		
		m.drawModel(gl);
		m.drawNormalVectors(gl);
		m.drawVertices(gl, c, .2, true, 1000);
	}
	private void drawGrid(GL gl)
	{
		gl.glPushMatrix();
		gl.glColor3d(0, 255, 0);
		gl.glTranslated(-ModelEditorConstants.gridWidth/2, 0, -ModelEditorConstants.gridDepth/2);
		gl.glBegin(GL.GL_LINES);
		for(int x = 0; x <= ModelEditorConstants.gridWidth; x+=ModelEditorConstants.gridSpacing)
		{
			gl.glVertex3d(x, 0, 0);
			gl.glVertex3d(x, 0, ModelEditorConstants.gridDepth);
		}
		for(int z = 0; z <= ModelEditorConstants.gridDepth; z+=ModelEditorConstants.gridSpacing)
		{
			gl.glVertex3d(0, 0, z);
			gl.glVertex3d(ModelEditorConstants.gridWidth, 0, z);
		}
		gl.glEnd();
		gl.glPopMatrix();
	}
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2){}
	public void init(GLAutoDrawable d)
	{
		GL gl = d.getGL();
		gl.glEnable(GL.GL_DEPTH_TEST);
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
}
