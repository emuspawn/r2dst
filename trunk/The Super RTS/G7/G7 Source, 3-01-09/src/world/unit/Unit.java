package world.unit;

import java.awt.Graphics2D;
import graphics.Camera;
import utilities.Location;
import world.owner.Owner;
import java.awt.Color;
import world.Element;
import world.WorldConstants;
import world.shot.weapon.Weapon;

/**
 * the units used in the game (buildings are units)
 * @author Jack
 *
 */
public class Unit extends Element
{
	int maxLife;
	int life;
	int viewRange;
	//protected int range; //how far the unit's weapon can shoot
	//protected int damage;
	protected int movement = 7;
	protected boolean isBuilder = false;
	protected boolean isGatherer = false; //whether or not the unit can gather resources
	
	//protected int gatheringRange = 0; //how far the unit must be to gather resources
	
	int buildTime = 50; //how many iterations of the main thread to build
	protected BuildTree bt;
	
	//Resource r; //he resource the unit is burrently holding if its a gatherer (null if empty)
	int massHolding = 0; //how much the unit is currently holding (to be deposited)
	protected int maxMassHolding = 0; //how much the unit can hold max
	
	
	protected int populationAugment = 0; //how much the unit contributes to the pop cap
	protected int populationValue = 1; //how much the unit takes up out of the pop cap
	int cost;
	
	protected Weapon weapon;
	//int shotCount = 0;
	//int maxShotCount = 300; //how many iterations before the unit can fire again
	//boolean weaponFired = false;
	
	//building variables
	protected boolean isBuilding = false;
	
	/**
	 * used in the ai so that the user can label units, does not affect gameplay,
	 * starts defaultly as nothing
	 */
	String description;
	
	public Unit(Owner owner, Location location, String name, int maxLife, int viewRange, Weapon weapon, int cost, int size)
	{
		super(name, location, size, size);
		if(owner != null)
		{
			this.owner = owner;
		}
		this.maxLife = maxLife;
		life = maxLife;
		this.viewRange = viewRange;
		this.weapon = weapon;
		this.cost = cost;
	}
	public Weapon getWeapon()
	{
		return weapon;
	}
	public boolean is(String type)
	{
		return getName().equalsIgnoreCase(type);
	}
	/**
	 * gets the amount of mass reuqired to build the unit
	 * @return returns the cost of the unit
	 */
	public int getCost()
	{
		return cost;
	}
	/**
	 * sets the amount of mass the unit is currently holding
	 * @param setter the new amount the unit is holding
	 */
	public void setMassHolding(int setter)
	{
		massHolding = setter;
		if(massHolding > maxMassHolding)
		{
			massHolding = maxMassHolding;
		}
	}
	/**
	 * gets the amount of mass the unit is holding
	 * @return returns the amount of mass the unit is holding
	 */
	public int getMassHolding()
	{
		return massHolding;
	}
	public int getPopulationAugment()
	{
		return populationAugment;
	}
	public int getPopulationValue()
	{
		return populationValue;
	}
	/*public int getDamage()
	{
		return damage;
	}
	public int getRange()
	{
		return range;
	}*/
	/*public void updateShotCount()
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
	}*/
	public int getLife()
	{
		return life;
	}
	public void setLife(int setter)
	{
		life = setter;
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
		
		if(WorldConstants.drawUnitViewRanges)
		{
			Color color = owner.getColor();
			g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 30));
			g.drawOval(x-viewRange, y-viewRange, viewRange*2, viewRange*2);
		}
		
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
