package world.shot;

import javax.media.opengl.GL;
import utilities.Location;
import world.Element;
import world.owner.Owner;

public class Shot extends Element
{
	private Location target;
	private double damage;
	private double movement;
	
	/**
	 * creates a shot for storage in the shot factory to be loaded by other classes
	 * @param name
	 * @param damage
	 * @param movement
	 * @param width
	 * @param height
	 * @param depth
	 */
	public Shot(String name, double damage, double movement, double width, double height, double depth)
	{
		super(name, null, null, 0, width, height, depth);
		this.damage = damage;
		this.movement = movement;
	}
	/**
	 * creates a shot to be used in the game
	 * @param location
	 * @param target
	 * @param s
	 * @param owner
	 */
	public Shot(Location location, Location target, Shot s, Owner owner)
	{
		super(s.getName(), location, owner, 0, s.getWidth(), s.getHeight(), s.getDepth());
		this.target = new Location(target.x, target.y, target.z);
		damage = s.getDamage();
		movement = s.getMovement();
	}
	public Location getTarget()
	{
		return target;
	}
	public double getMovement()
	{
		return movement;
	}
	public double getDamage()
	{
		return damage;
	}
	public void drawElement(GL gl)
	{
		gl.glPushMatrix();
		/*gl.glTranslated(getLocation().x, getLocation().y, getLocation().z);
		gl.glScaled(getWidth(), getHeight(), getDepth());
		gl.glBegin(GL.GL_QUAD_STRIP);
		  //Quads 1 2 3 4
		    gl.glVertex3f( 1.0f, 1.0f, 1.0f);   //V2
		    gl.glVertex3f( 1.0f,-1.0f, 1.0f);   //V1
		    gl.glVertex3f( 1.0f, 1.0f,-1.0f);   //V4
		    gl.glVertex3f( 1.0f,-1.0f,-1.0f);   //V3
		    gl.glVertex3f(-1.0f, 1.0f,-1.0f);   //V6
		    gl.glVertex3f(-1.0f,-1.0f,-1.0f);   //V5
		    gl.glVertex3f(-1.0f, 1.0f, 1.0f);   //V8
		    gl.glVertex3f(-1.0f,-1.0f, 1.0f);   //V7
		    gl.glVertex3f( 1.0f, 1.0f, 1.0f);   //V2
		    gl.glVertex3f( 1.0f,-1.0f, 1.0f);   //V1
		gl.glEnd();
		gl.glBegin(GL.GL_QUADS);
		  //Quad 5
		    gl.glVertex3f(-1.0f, 1.0f,-1.0f);   //V6
		    gl.glVertex3f(-1.0f, 1.0f, 1.0f);   //V8
		    gl.glVertex3f( 1.0f, 1.0f, 1.0f);   //V2
		    gl.glVertex3f( 1.0f, 1.0f,-1.0f);   //V4
		  //Quad 6
		    gl.glVertex3f(-1.0f,-1.0f, 1.0f);   //V7
		    gl.glVertex3f(-1.0f,-1.0f,-1.0f);   //V5
		    gl.glVertex3f( 1.0f,-1.0f,-1.0f);   //V3
		    gl.glVertex3f( 1.0f,-1.0f, 1.0f);   //V1
		gl.glEnd();*/
		
		gl.glColor3d(0, 0, 0);
		
		drawPrism(gl);
		gl.glPopMatrix();
	}
}
