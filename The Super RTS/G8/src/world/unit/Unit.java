package world.unit;

import java.util.ArrayList;
import java.util.Iterator;

import javax.media.opengl.GL;

import sgEngine.EngineConstants;
import utilities.Location;
import world.Element;
import world.owner.Owner;
import world.shot.weapon.Weapon;

public class Unit extends Element
{
	private double movement;
	private boolean selected = false;
	private Weapon w;
	private int id;
	private ArrayList<String> buildTree;
	private int buildTime; //how many iterations in order to build the unit
	
	private double metalDrain; //how much metal this unit generates for its owner
	private double energyDrain; //how much energy this unit generates for its owner (or costs to run)
	private double metalCost; //metal cost to produce this unit
	private double energyCost; //energy cost to produce this unit
	
	/**
	 * makes a new unit with the passed traits to be stored for the creation of other units
	 * @param name
	 * @param life
	 * @param movement
	 * @param metalDrain
	 * @param energyDrain
	 * @param metalCost
	 * @param energyCost
	 * @param width
	 * @param height
	 */
	public Unit(String name, Weapon w, double life, 
			double movement, double energyCost, double metalCost, 
			double energyDrain, double metalDrain, double width, double height, 
			double depth, int buildTime)
	{
		super(name, null, null, life, width, height, depth);
		this.movement = movement;
		this.w = w;
		this.metalDrain = metalDrain;
		this.energyDrain = energyDrain;
		this.metalCost = metalCost;
		this.energyCost = energyCost;
	}
	public int getBuildTime()
	{
		return buildTime;
	}
	/**
	 * makes a new unit that is a copy of the passed unit to be used in the game
	 * @param u the unit to be copied
	 * @param owner the owner of the new unit
	 * @param l the location of the new unit
	 */
	public Unit(Unit u, Owner owner, Location l)
	{
		super(u.getName(), l, owner, u.getLife(), u.getWidth(), u.getHeight(), u.getDepth());
		movement = u.getMovement();
		w = u.getWeapon();
		metalDrain = u.getMetalDrain();
		energyDrain = u.getEnergyDrain();
		metalCost = u.getMetalCost();
		energyCost = u.getEnergyCost();
		buildTime = u.getBuildTime();
		setBuildTree(u.getBuildTree());
	}
	/**
	 * sets this unit's build tree
	 * @param al
	 */
	public void setBuildTree(ArrayList<String> al)
	{
		buildTree = al;
	}
	public ArrayList<String> getBuildTree()
	{
		return buildTree;
	}
	/**
	 * checks the build tree of the unit to determine if it can build that passed unit
	 * @param name the name of the unit being checked
	 * @return returns true if this unit can build the passed unit, false otherwise
	 */
	public boolean canBuild(String name)
	{
		Iterator<String> i = buildTree.iterator();
		while(i.hasNext())
		{
			if(i.next().equalsIgnoreCase(name))
			{
				return true;
			}
		}
		return false;
	}
	public int getID()
	{
		return id;
	}
	/**
	 * sets the id of the unit, set when registered in the UnitEngine
	 * @param setter
	 */
	public void setID(int setter)
	{
		id = setter;
	}
	/**
	 * the height at which the unit should remain such above its location in order
	 * to have its full bounds displayed
	 * @return
	 */
	public double getRestingHeight()
	{
		return getHeight()/2;
	}
	/**
	 * gets the weapon used by this unit
	 * @return returns the unit's weapon
	 */
	public Weapon getWeapon()
	{
		return w;
	}
	/**
	 * how much metal a given unit drains per iteration of the main SGEninge thread,
	 * if the owner of the unit does not have enough of this resource than this unit
	 * does not function
	 * @return returns the unit's metal drain
	 */
	public double getMetalDrain()
	{
		return metalDrain;
	}
	/**
	 * how much energy a given unit drains per iteration of the main SGEninge thread,
	 * if the owner of the unit does not have enough of this resource than this unit
	 * does not function
	 * @return returns the unit's energy drain
	 */
	public double getEnergyDrain()
	{
		return energyDrain;
	}
	/**
	 * gets how much metal an owner must have in reserve in order to build this unit
	 * @return returns the unit's metal cost
	 */
	public double getMetalCost()
	{
		return metalCost;
	}
	/**
	 * gets how much energy an owner must have in reserve in order to build this unit
	 * @return returns the unit's energy cost
	 */
	public double getEnergyCost()
	{
		return energyCost;
	}
	/**
	 * checks to see if the unit is selected
	 * @return returns true if the unit is selected,
	 * false otherwise
	 */
	public boolean isSelected()
	{
		return selected;
	}
	/**
	 * sets whether or not the unit is selected
	 * @param setter
	 */
	public void setSelected(boolean setter)
	{
		selected = setter;
	}
	public void drawElement(GL gl)
	{
		Location l = getLocation();
		double width = getWidth();
		//double height = getHeight();
		double depth = getDepth();
		
		Owner owner = getOwner();
		gl.glColor3d(owner.getColor().getRed(), owner.getColor().getGreen(), owner.getColor().getBlue());
		
		if(selected && EngineConstants.selectedUnitsAreWhite)
		{
			gl.glColor3d(128, 128, 128);
		}
		
		/*gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(l.x-width/2, l.y, l.z-depth/2);
		gl.glVertex3d(l.x+width/2, l.y, l.z-depth/2);
		gl.glVertex3d(l.x+width/2, l.y, l.z+depth/2);
		gl.glVertex3d(l.x-width/2, l.y, l.z+depth/2);
		gl.glEnd();*/
		this.drawPrism(gl);
		
		if(selected)
		{
			gl.glLineWidth(2);
			gl.glColor3d(255, 128, 0);
			gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex3d(l.x-width/1.5, l.y, l.z-depth/1.5);
			gl.glVertex3d(l.x+width/1.5, l.y, l.z-depth/1.5);
			gl.glVertex3d(l.x+width/1.5, l.y, l.z+depth/1.5);
			gl.glVertex3d(l.x-width/1.5, l.y, l.z+depth/1.5);
			gl.glEnd();
		}
	}
	public double getMovement()
	{
		return movement;
	}
}
