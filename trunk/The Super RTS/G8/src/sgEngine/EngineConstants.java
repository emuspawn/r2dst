package sgEngine;

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
	public static final boolean startUnitMapDisplayWindow = false; //displays the unit map for diagnostic purposes
	public static final boolean selectedUnitsAreWhite = true;
}
