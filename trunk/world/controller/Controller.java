package world.controller;

import java.awt.Color;

public class Controller
{
	Color c;
	int playerNumber;
	
	public Controller(int playerNumber, Color c)
	{
		this.playerNumber = playerNumber;
		this.c = c;
	}
	public Color getPlayerColor()
	{
		return c;
	}
	public int getPlayerNumber()
	{
		return playerNumber;
	}
}
