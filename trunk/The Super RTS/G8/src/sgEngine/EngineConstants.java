package sgEngine;

import utilities.Location;
import world.unit.UnitFactory;

/**
 * holds various world constants for use throughout the program
 * @author Jack
 *
 */
public final class EngineConstants
{
	public static UnitFactory unitFactory = new UnitFactory();
	public static final boolean drawShots = true;
	public static final Location mapCenter = new Location(0, 0, 0);
	public static final boolean startUnitMapDisplayWindow = false; //displays the unit map for diagnostic purposes
	public static final boolean selectedUnitsAreWhite = true;
	public static final double startingMetal = 1000;
	public static final double startingEnergy = 1000;
	public static final boolean cameraMode = true; //certain things arent drawn in camera mode to add to the games visuals
}
