package sgEngine;

import utilities.Location;
import world.shot.ShotFactory;
import world.shot.weapon.WeaponFactory;
import world.unit.UnitFactory;

/**
 * holds various world constants for use throughout the program
 * @author Jack
 *
 */
public final class EngineConstants
{
	public static ShotFactory shotFactory = new ShotFactory();
	public static WeaponFactory weaponFactory = new WeaponFactory();
	public static UnitFactory unitFactory = new UnitFactory();
	
	public static final boolean drawShots = true;
	public static final Location mapCenter = new Location(0, 0, 0);
	public static final boolean startUnitMapDisplayWindow = false; //displays the unit map for diagnostic purposes
	public static final boolean selectedUnitsAreWhite = true;
	public static final double startingMetal = 1000;
	public static final double startingEnergy = 1000;
	public static boolean cameraMode = false; //certain things arent drawn in camera mode to add to the games visuals
	public static final int maxPopulation = 20; //roughly
}
