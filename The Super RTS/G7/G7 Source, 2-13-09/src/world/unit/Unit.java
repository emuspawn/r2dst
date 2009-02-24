package world.unit;

import java.awt.Graphics2D;
import world.resource.Resource;
import graphics.Camera;
import utilities.Location;
import world.owner.Owner;
import world.action.Action;
import java.awt.Color;
import world.action.actions.Idle;
import world.Element;

public class Unit extends Element
{
	int maxLife;
	int life;
	int viewRange;
	protected int range; //how far the unit's weapon can shoot
	protected int damage;
	Action a = new Idle();
	protected int movement = 7;
	protected boolean isBuilder = false;
	protected boolean isGatherer = false; //whether or not the unit can gather resources
	protected int gatheringRange = 0; //how far the unit must be to gather resources
	int buildTime = 50; //how many iterations of the main thread to build
	protected BuildTree bt;
	Resource r; //he resource the unit is burrently holding if its a gatherer (null if empty)
	
	protected int populationAugment = 0; //how much the unit contributes to the pop cap
	protected int populationValue = 1; //how much the unit takes up out of the pop cap
	int cost;
	
	int shotCount = 0;
	int maxShotCount = 300; //how many iterations before the unit can fire again
	boolean weaponFired = false;
	
	//building variables
	protected boolean isBuilding = false;
	
	String label; //used in the ai so that the user can label units, does not affect gameplay
	
	public Unit(Owner owner, Location location, String name, int maxLife, int viewRange, int range, int damage, int cost, int size)
	{
		super(name, location, size, size);
		this.owner = owner;
		this.maxLife = maxLife;
		life = maxLife;
		this.viewRange = viewRange;
		this.range = range;
		this.damage = damage;
		this.cost = cost;
	}
	public boolean is(String type)
	{
		return getName().equalsIgnoreCase(type);
	}
	public int getCost()
	{
		return cost;
	}
	public int getPopulationAugment()
	{
		return populationAugment;
	}
	public int getPopulationValue()
	{
		return populationValue;
	}
	public int getDamage()
	{
		return damage;
	}
	public int getRange()
	{
		return range;
	}
	public void updateShotCount()
	{
		shotCount++;
		if(shotCount == maxShotCount)
		{
			shotCount = 0;
			weaponFired = false;
		}
	}
	public void fireWeapon()
	{
		weaponFired = true;
	}
	public boolean getWeaponFired()
	{
		return weaponFired;
	}
	public int getLife()
	{
		return life;
	}
	public void setLife(int setter)
	{
		life = setter;
	}
	public void setResource(Resource r)
	{
		this.r = r;
	}
	public Resource getResource()
	{
		return r;
	}
	public int getViewRange()
	{
		return viewRange;
	}
	public int getBuildTime()
	{
		return buildTime;
	}
	public int getMovement()
	{
		return movement;
	}
	public void drawElement(Graphics2D g, Camera c)
	{
		Location location = getLocation();
		int size = getWidth();
		String name = getName();
		
		g.setColor(owner.getColor());
		int x = c.getScreenLocation(location).x;
		int y = c.getScreenLocation(location).y;
		g.fillRect(x-size/2, y-size/2, size, size);
		try
		{
			String sub = name.substring(0, 3);
			g.setColor(Color.black);
			g.drawString(sub, x-9, y);
			g.drawRect(x-size/2, y-size/2, size, size);
		}
		catch(StringIndexOutOfBoundsException e)
		{
			String sub = name.substring(0, 1);
			g.setColor(Color.black);
			g.drawString(sub, x-9, y);
			g.drawRect(x-size/2, y-size/2, size, size);
		}
	}
}
