package gameEngine.world.shot;

import gameEngine.world.owner.Owner;
import gameEngine.world.unit.Unit;
import javax.media.opengl.GL;

import utilities.Region;

/**
 * defines the shot class
 * 
 * -a shot can only hit one other unit
 * -the unit the shot hits must be of a different owner
 * @author Jack
 *
 */
public abstract class Shot extends Region
{
	double movement;
	boolean dead = false;
	double damage;
	Owner o;
	long ct;
	
	/**
	 * creates a new shot
	 * @param x the x position of the location of the unit when the shot was created/fired
	 * @param y
	 * @param width
	 * @param height
	 * @param target the target the shot was fired at
	 * @param movement how fast the shot moves; in pixels a second
	 */
	public Shot(double x, double y, double width, double height, Unit target, double movement, double damage, Owner o)
	{
		super(x, y, width, height);
		this.movement = movement;
		this.damage = damage;
		this.o = o;
		ct = System.currentTimeMillis();
	}
	public void setDead()
	{
		dead = true;
	}
	/**
	 * gets the owner of the shot, the owner of the shot is the
	 * same as the owner of the unit that fired the shot
	 * @return returns the owner of the shot
	 */
	public Owner getOwner()
	{
		return o;
	}
	public double getDamage()
	{
		return damage;
	}
	public void drawShot(GL gl)
	{
		gl.glPushMatrix();
		gl.glColor3d(1, 1, 0);
		drawRegion(gl);
		gl.glPopMatrix();
	}
	/**
	 * updates the position of the shot
	 * @param tdiff the time passed since the last update call
	 */
	public abstract void updateShot(double tdiff);
	public boolean isDead()
	{
		return dead;
	}
}
