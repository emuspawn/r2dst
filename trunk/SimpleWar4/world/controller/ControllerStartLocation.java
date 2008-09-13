package world.controller;

import utilities.Location;

//where the controller gets its initial units at the start of a new game

public class ControllerStartLocation
{
	int player; //the player the start locations are for
	Location dal; //divine anchor location
	Location cl; //castle location
	
	public ControllerStartLocation(int player, Location divineAnchorLocation, Location castleLocation)
	{
		this.player = player;
		dal = divineAnchorLocation;
		cl = castleLocation;
	}
	public Location getDivineAnchorLocation()
	{
		return dal;
	}
	public Location getCastleLocation()
	{
		return cl;
	}
	public int getPlayerFor()
	{
		return player;
	}
}
