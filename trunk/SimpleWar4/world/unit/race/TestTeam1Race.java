package world.unit.race;

import world.unit.building.testTeam1Buildings.*;
import world.unit.building.*;
import utilities.Location;
import world.controller.*;
import graphics.Camera;
import world.unit.*;

//records the unit hierarchies of a particular race

public class TestTeam1Race extends Race
{
	public TestTeam1Race(){}
	public Unit getCastle(Camera camera, Controller c, Location l)
	{
		return new Castle(camera, c, l);
	}
	public Unit getDivineAnchor(Camera camera, Controller c, Location l)
	{
		return new DivineAnchor(camera, c, l);
	}
}
