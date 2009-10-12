package graphics;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import utilities.Location;

/**
 * a camera meant for use with OpenGL
 * @author Jack
 *
 */
public class GLCamera
{
	private Location cLocation; //camera location
	private Location vLocation; //view location
	private Location upVector;
	private double width; //view width
	private double height; //view height
	private double rotate = 0;
	
	//matrices for unprojecting
	private double[] projection;
	private double[] model;
	private int[] viewport;
	
	/**
	 * creates a new camera
	 * @param cLocation
	 * @param vLocation
	 * @param viewingWidth the width of the viewing region
	 * @param viewingHeight the height of the vewing region
	 */
	public GLCamera(Location cLocation, Location vLocation, double viewingWidth, double viewingHeight)
	{
		this.cLocation = cLocation;
		this.vLocation = vLocation;
		this.width = viewingWidth;
		this.height = viewingHeight;
		
		Location l = new Location(vLocation.x-1, vLocation.y, vLocation.z);
		upVector = determineNormal(vLocation, cLocation, l);
		upVector = new Location(upVector.x*-1, upVector.y*-1, upVector.z*-1);
	}
	/**
	 * centers the cameras view on the passed location, the
	 * angle of the viewing is unchanged
	 * @param l
	 */
	public void centerView(Location l)
	{
		double xover = l.x-vLocation.x;
		double yover = l.y-vLocation.y;
		double zover = l.z-vLocation.z;
		translate(xover, yover, zover);
	}
	/**
	 * sets the width and height of the viewing region
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		//System.out.println("camera size = "+width+", "+height);
	}
	/**
	 * loads an identity for the model view matrix and sets
	 * it to the camera's view (including rotation)
	 * @param gl
	 */
	public void loadMatrices(GL gl)
	{
		GLU glu = new GLU();
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(cLocation.x, cLocation.y, cLocation.z, 
				vLocation.x, vLocation.y, vLocation.z, upVector.x, upVector.y, upVector.z);
		gl.glRotated(rotate, 0, 1, 0);
		
		Location l = new Location(vLocation.x-1, vLocation.y, vLocation.z);
		upVector = determineNormal(vLocation, cLocation, l);
		upVector = new Location(upVector.x*-1, upVector.y*-1, upVector.z*-1);
	}
	/**
	 * determines the normal of the surface defined by the three points
	 * @param p1
	 * @param p2
	 * @param p3
	 * @return returns a normalized normal vector
	 */
	private Location determineNormal(Location p1, Location p2, Location p3)
	{
		Location v1 = new Location(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z); //vector 1
		Location v2 = new Location(p3.x-p1.x, p3.y-p1.y, p3.z-p1.z);
		Location normal = new Location(v1.y*v2.z-v2.y*v1.z, v2.x*v1.z-v1.x*v2.z, v1.x*v2.y-v2.x*v1.y);
		//System.out.println("v1 = "+v1);
		//System.out.println("v2 = "+v2);
		//System.out.println("normal = "+normal);
		double length = Math.sqrt(Math.pow(normal.x, 2)+Math.pow(normal.y, 2)+Math.pow(normal.z, 2));
		normal = new Location(normal.x/length, normal.y/length, normal.z/length);
		return normal;
	}
	/**
	 * gets how far the camera is rotated about its viewpoint
	 * @return returns the camera's rotation angle
	 */
	public double getRotation()
	{
		return rotate;
	}
	/**
	 * rotates the camera around its view location, rotates
	 * on the x and y axes
	 * @param angle angle in degrees
	 */
	public void rotate(double angle)
	{
		//cLocation.rotate2D(vLocation, angle);
		rotate+=angle;
	}
	public Location getLocation()
	{
		return cLocation;
	}
	public Location getViewLocation()
	{
		return vLocation;
	}
	public double getWidth()
	{
		return width;
	}
	public double getHeight()
	{
		return height;
	}
	/**
	 * zooms the camera, moves the camera along the line
	 * formed by the view viector and the camera location
	 * @param dist distance to be zoomed
	 */
	public void zoom(double dist)
	{
		double xover = (cLocation.x - vLocation.x)*dist;
		double yover = (cLocation.y - vLocation.y)*dist;
		double zover = (cLocation.z - vLocation.z)*dist;
		translate(xover, yover, zover);
	}
	/**
	 * translates the camera location and view location
	 */
	public void translate(double xover, double yover, double zover)
	{
		cLocation.translate(xover, yover, zover);
		vLocation.translate(xover, yover, zover);
	}
	/**
	 * updates the prjection matrix for the camera, needed for
	 * unprojecting
	 * @param gl
	 */
	private void updateProjectionMatrix(GL gl)
	{
		projection = new double[16];
		gl.glGetDoublev(GL.GL_PROJECTION_MATRIX, projection, 0);
	}
	/**
	 * updates the model view matrix, needed for unprojecting
	 * @param gl
	 */
	private void updateModelMatrix(GL gl)
	{
		model = new double[16];
		gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, model, 0);
	}
	/**
	 * updates the viewport of the camera, needed for unprojecting
	 * @param gl
	 */
	private void updateViewport(GL gl)
	{
		viewport = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
	}
	/**
	 * updates various camera features such as viewport, projection matrix,
	 * model view matrix, etc, needed to unproject points
	 * @param gl
	 */
	public void updateCamera(GL gl)
	{
		updateProjectionMatrix(gl);
		updateModelMatrix(gl);
		updateViewport(gl);
	}
	/**
	 * converts a location in 3d to a location in 2d based off the camera position and
	 * view location, the projection and model matrices must be updated regularly for
	 * this to work, viewport must be updated regularly for this to work
	 * @param l the 3d location
	 * @param d
	 * @return
	 */
	public Location project(Location l)
	{
		try
		{
			GLU glu = new GLU();
			double[] points = new double[4];
			glu.gluProject(l.x, l.y, l.z, model, 0, projection, 0, viewport, 0, points, 0);
			return new Location(points[0], points[1]);
		}
		catch(NullPointerException e)
		{
			return new Location(0, 0);
		}
	}
	/**
	 * gets the location in game space of where the mouse was clicked
	 * based off the passed height and the position of the camera
	 * @param mouseClick where the mouse was clicked
	 * @param height the height of the point
	 * @return the location in game space of where the center of the camera's
	 * view is
	 */
	public Location getMapLocation(Location mc, double height)
	{
		try
		{
			GLU glu = new GLU();
			Location mouseClick = new Location(mc.x, mc.y, mc.z);
			double[] points = new double[4];
			//System.out.println("screen height = "+height);
			mouseClick.y = getHeight()-mouseClick.y;
			//System.out.println("mouse click = "+mouseClick);
			glu.gluUnProject(mouseClick.x, mouseClick.y, mouseClick.z, model, 0, projection, 0, viewport, 0, points, 0);
			Location l = new Location(points[0], points[1], points[2]);
			//System.out.println("first location = "+l);
			Location vector = new Location(l.x-cLocation.x, l.y-cLocation.y, l.z-cLocation.z);
			double lambda = (height-l.y)/vector.y;
			double x = l.x+lambda*vector.x;
			double z = l.z+lambda*vector.z;
			//System.out.println("returning "+new Location(x, height, z));
			return new Location(x, height, z);
		}
		catch(NullPointerException e)
		{
			return new Location(0, 0, 0);
		}
	}
}