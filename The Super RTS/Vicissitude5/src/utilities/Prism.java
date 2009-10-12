package utilities;

import javax.media.opengl.GL;

/**
 * represents a region of 3d space
 * @author Jack
 *
 */
public class Prism
{
	/**
	 * actual location stored is the back most (-z) bottom (-y) left (-x)
	 * location on the prism, stored this way for simplification of intersection evaluation
	 */
	Location l;
	double width;
	double height;
	double depth;
	
	/**
	 * creates a new prism
	 * @param l the location of the center of the prism
	 * @param width
	 * @param height
	 * @param depth
	 */
	public Prism(Location l, double width, double height, double depth)
	{
		if(l != null)
		{
			this.l = new Location(l.x-width/2, l.y-height/2, l.z - depth/2);
		}
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	/**
	 * translates the prism
	 * @param xover
	 * @param yover
	 * @param zover
	 */
	public void translate(double xover, double yover, double zover)
	{
		Location l = getLocation();
		l.translate(xover, yover, zover);
		setLocation(l);
	}
	/**
	 * sets the location of the center of the prism
	 * @param l the new location of the center of the prism
	 */
	public void setLocation(Location l)
	{
		this.l = new Location(l.x-width/2, l.y-height/2, l.z - depth/2);
	}
	public double getWidth()
	{
		return width;
	}
	public double getHeight()
	{
		return height;
	}
	public double getDepth()
	{
		return depth;
	}
	/**
	 * gets the location of the center of the prism
	 * @return returns the location of the center of the prism
	 */
	public Location getLocation()
	{
		return new Location(l.x+width/2, l.y+height/2, l.z+depth/2);
	}
	/**
	 * determines whether or not this prism contains a given location
	 * @param l the location to be tested
	 * @return returns true if the prism contains the passed location, false otherwise
	 */
	public boolean contains(Location l)
	{
		return intersects(new Prism(l, 0, 0, 0));
	}
	/**
	 * determines if this prism intersects the passed prism
	 * @param p the other prism with which this one is to be checked with
	 * @return returns true if this prism intersects the passed one, false otherwise
	 */
	public boolean intersects(Prism p)
	{
		double leftx = 0;
		double rightx = 0;
		double leftWidth = 0;
		if(l.x <= p.getLocation().x-p.getWidth()/2)
		{
			leftx = l.x;
			rightx = p.getLocation().x-p.getWidth()/2;
			leftWidth = width;
		}
		else
		{
			leftx = p.getLocation().x-p.getWidth()/2;
			rightx = l.x;
			leftWidth = p.getWidth();
		}
		if(rightx >= leftx && rightx <= leftx+leftWidth)
		{
			double bottomy = 0;
			double topy = 0;
			double bottomHeight = 0;
			if(l.y <= p.getLocation().y-p.getHeight()/2)
			{
				bottomy = l.y;
				topy = p.getLocation().y-p.getHeight()/2;
				bottomHeight = height;
			}
			else
			{
				bottomy = p.getLocation().y-p.getHeight()/2;
				topy = l.y;
				bottomHeight = p.getHeight();
			}
			if(topy >= bottomy && topy <= bottomy+bottomHeight)
			{
				double backz = 0;
				double frontz = 0;
				double backDepth = 0;
				if(l.z <= p.getLocation().z-p.getDepth()/2)
				{
					backz = l.z;
					frontz = p.getLocation().z-p.getDepth()/2;
					backDepth = depth;
				}
				else
				{
					backz = p.getLocation().z-p.getDepth()/2;
					frontz = l.z;
					backDepth = p.getDepth();
				}
				if(frontz >= backz && frontz <= backz+backDepth)
				{
					return true;
				}
			}
		}
		return false;
	}
	public void drawPrism(GL gl)
	{
		gl.glPushMatrix();
		gl.glTranslated(l.x+width/2, l.y+height/2, l.z+depth/2);
		gl.glScaled(width, height, depth);
		double w = .5;
		gl.glBegin(GL.GL_QUAD_STRIP);
		gl.glVertex3d( w, w, w);   //V2
		gl.glVertex3d( w,-w, w);   //V1
		gl.glVertex3d( w, w,-w);   //V4
		gl.glVertex3d( w,-w,-w);   //V3
		gl.glVertex3d(-w, w,-w);   //V6
		gl.glVertex3d(-w,-w,-w);   //V5
		gl.glVertex3d(-w, w, w);   //V8
		gl.glVertex3d(-w,-w, w);   //V7
		gl.glVertex3d( w, w, w);   //V2
		gl.glVertex3d( w,-w, w);   //V1
		gl.glEnd();
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(-w, w,-w);   //V6
		gl.glVertex3d(-w, w, w);   //V8
		gl.glVertex3d( w, w, w);   //V2
		gl.glVertex3d( w, w,-w);   //V4
		gl.glVertex3d(-w,-w, w);   //V7
		gl.glVertex3d(-w,-w,-w);   //V5
		gl.glVertex3d( w,-w,-w);   //V3
		gl.glVertex3d( w,-w, w);   //V1
		gl.glEnd();
		gl.glPopMatrix();
	}
	public String toString()
	{
		return "l="+l+", w="+width+", h="+height+", d="+depth;
	}
	public static void main(String[] args)
	{
		Prism p1 = new Prism(new Location(0, 0, 0), 10, 10, 10);
		Prism p2 = new Prism(new Location(0, -5.0, 0), 0, 0, 0);
		System.out.println("p1 intersects p2 = "+p1.intersects(p2));
		System.out.println("p1 = "+p1);
		System.out.println("p2 = "+p2);
	}
}
