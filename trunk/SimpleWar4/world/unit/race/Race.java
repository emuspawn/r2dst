package world.unit.race;

import world.unit.Unit;
import utilities.Location;
import world.controller.*;
import graphics.Camera;

//records the unit hierarchies of a particular race

public abstract class Race
{
	public Race(){}
	public abstract Unit getCastle(Camera camera, Controller c, Location l);
	public abstract Unit getDivineAnchor(Camera camera, Controller c, Location l);
}
