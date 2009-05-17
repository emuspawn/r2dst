package world.shot;

import javax.media.opengl.GL;
import utilities.Location;
import world.Element;
import world.owner.Owner;

public class Shot extends Element
{
	private Location target;
	private int damage;
	private double movement;
	
	public Shot(Location location, Location target, Owner owner, int damage, double movement, double width, double height, double depth)
	{
		super("shot", location, owner, 0, width, height, depth);
		if(target != null)
		{
			this.target = new Location(target.x, target.y, target.z);
		}
		this.damage = damage;
		this.movement = movement;
	}
	public Shot(Location location, Location target, Shot s, Owner owner)
	{
		super("shot", location, owner, 0, s.getWidth(), s.getHeight(), s.getDepth());
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
	public int getDamage()
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
		
		this.drawPrism(gl);
		gl.glPopMatrix();
	}
}
