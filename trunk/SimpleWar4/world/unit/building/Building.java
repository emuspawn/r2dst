package world.unit.building;

import world.unit.Unit;
import java.awt.Graphics;
import utilities.Location;
import graphics.Camera;
import world.controller.*;
import java.awt.Rectangle;

public class Building extends Unit
{
	Rectangle buildBounds; //the bounds of the building where units can cross but other buildings cant be built
	
	public Building(Camera camera, Controller c, Location location)
	{
		super(camera, c, location);
	}
	public void drawUnit(Graphics g)
	{
		
	}
}
