package mapEditor;

import java.util.HashMap;

import javax.media.opengl.GL;

import mapEditor.mapEditorMenuBar.MapEditorMenuBar;
import ui.UIFrame;
import ui.display.Displayable;
import ui.userIO.UserInputInterpreter;
import utilities.Location;
import utilities.MathUtil;
import utilities.Polygon;
import utilities.Region;

public class MapEditor extends UserInputInterpreter implements Displayable
{
	HashMap<Character, Integer> modes = new HashMap<Character, Integer>();
	private final static int NO_MODE = 0;
	private final static int RECTANGLE_MODE = 1;
	private final static int START_LOCATION_MODE = 6;
	
	//moving the view area
	private final static int MOVE_UP = 2;
	private final static int MOVE_RIGHT = 3;
	private final static int MOVE_DOWN = 4;
	private final static int MOVE_LEFT = 5;
	double xt = 0; //x translational amount of the view area
	double yt = 0;
	
	
	int mode = NO_MODE;
	boolean dragging = false;
	int[] dstart; //the starting position of the mouse drag
	int[] mcurrent = new int[2]; //the current position of the mouse
	
	Rectangle[] r = new Rectangle[30];
	int rindex = 0;
	
	Map m = new Map();
	
	public MapEditor()
	{
		modes.put('r', RECTANGLE_MODE);
		modes.put('e', START_LOCATION_MODE);
		
		modes.put('w', MOVE_UP);
		modes.put('d', MOVE_RIGHT);
		modes.put('s', MOVE_DOWN);
		modes.put('a', MOVE_LEFT);
		
		UIFrame uif = new UIFrame(this, this);
		uif.setTitle("Map Editor");
		uif.setMenuBar(new MapEditorMenuBar(uif, m));
	}
	/**
	 * creates a rectangular polygon based off the passed parameters
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param width
	 * @return returns a polygon
	 */
	public Polygon calculatePolygon(double x1, double y1, double x2, double y2, double width)
	{
		//System.out.println("points = "+x1+", "+y1+", "+x2+", "+y2);
		double[][] p = new double[4][2]; //the vertices of the rectangle
		double[] normal = MathUtil.normal(x1, y1, x2, y2);
		p[0][0] = -normal[0]*width/2+x1;
		p[0][1] = -normal[1]*width/2+y1;
		p[1][0] = normal[0]*width/2+x1;
		p[1][1] = normal[1]*width/2+y1;
		p[2][0] = normal[0]*width/2+x2;
		p[2][1] = normal[1]*width/2+y2;
		p[3][0] = -normal[0]*width/2+x2;
		p[3][1] = -normal[1]*width/2+y2;
		
		Location[] vertices = new Location[p.length];
		for(int i = 0; i < vertices.length; i++)
		{
			vertices[i] = new Location(p[i][0], p[i][1]);
			//System.out.println(vertices[i]);
		}
		//System.out.println("bounds = "+Polygon.determineBoundingRegion(vertices));
		//System.out.println("--------------------");
		return new Polygon(Polygon.determineBoundingRegion(vertices), vertices);
	}
	public static void main(String[] args)
	{
		new MapEditor();
	}
	public void keyAction(char c, boolean pressed)
	{
		if(pressed)
		{
			mode = modes.get(c);
			double t = 10;
			if(mode == MOVE_UP)
			{
				yt+=t;
			}
			else if(mode == MOVE_DOWN)
			{
				yt-=t;
			}
			else if(mode == MOVE_RIGHT)
			{
				xt+=t;
			}
			else if(mode == MOVE_LEFT)
			{
				xt-=t;
			}
		}
	}
	public void mouseAction(int x, int y, boolean pressed, boolean rightClick)
	{
		if(!pressed && dragging)
		{
			dragging = false;
			if(mode == RECTANGLE_MODE)
			{
				if(!rightClick)
				{
					//r[rindex] = new Rectangle(x, y, dstart[0], dstart[1], 40);
					//rindex++;
					m.addPolygon(calculatePolygon(x+(int)xt, y+(int)yt, dstart[0], dstart[1], 40));
				}
			}
		}
		else if(pressed)
		{
			if(rightClick)
			{
				if(mode == RECTANGLE_MODE)
				{
					//System.out.println("x="+x+", y="+y);
					m.removePolygon(x, y);
				}
			}
			else
			{
				if(mode == START_LOCATION_MODE)
				{
					double w = 50;
					m.addStartLocation(new Region(x+xt-w/2, y+yt-w/2, w, w));
				}
			}
		}
	}
	public void mouseMoveAction(int x, int y, boolean dragged, boolean rightClick)
	{
		mcurrent[0] = x+(int)xt;
		mcurrent[1] = y+(int)yt;
		
		if(dragged && !dragging)
		{
			dstart = new int[2];
			dstart[0] = x+(int)xt;
			dstart[1] = y+(int)yt;
			dragging = true;
		}
	}
	public void display(GL gl, int viewWidth, int viewHeight)
	{
		gl.glTranslated(-xt, -yt, 0);
		if(dragging)
		{
			gl.glColor3d(1, 1, 1);
			gl.glBegin(GL.GL_LINES);
			gl.glVertex2d(dstart[0], dstart[1]);
			gl.glVertex2d(mcurrent[0], mcurrent[1]);
			gl.glEnd();
		}
		/*gl.glColor3d(0, 1, 0);
		for(int i = 0; i < rindex; i++)
		{
			r[i].drawRectangle(gl);
		}*/
		m.drawMapElements(gl);
	}
}
