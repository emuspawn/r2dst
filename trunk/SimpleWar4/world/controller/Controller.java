package world.controller;

import java.awt.Color;
import world.unit.race.*;

public class Controller
{
	Color c;
	int playerNumber;
	int team; //the team this player is on
	Race r = new TestTeam1Race(); //the group of units controlled by the player
	
	/*
	 * race:
	 * 1=test team 1
	 */
	
	public Controller(int playerNumber, Color c, Race r)
	{
		this.playerNumber = playerNumber;
		this.c = c;
		if(r != null)
		{
			this.r = r;
		}
	}
	public Color getPlayerColor()
	{
		return c;
	}
	public int getPlayerNumber()
	{
		return playerNumber;
	}
	public Race getRace()
	{
		return r;
	}
}
