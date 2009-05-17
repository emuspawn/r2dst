package world.owner;

import java.awt.Color;

import ai.AI;

public class Owner
{
	String name;
	Color c;
	
	/**
	 * the AI of the owner, if null the owner does nothing
	 */
	AI ai;
	
	public Owner(String name, Color c)
	{
		this.c = c;
		this.name = name;
	}
	/**
	 * sets the ai for this owner
	 * @param ai the ai this owner is to use
	 */
	public void setAI(AI ai)
	{
		this.ai = ai;
	}
	public Color getColor()
	{
		return c;
	}
	/**
	 * gets the ai this owner is using
	 * @return returns the ai for this owner
	 */
	public AI getAI()
	{
		return ai;
	}
	public String getName()
	{
		return name;
	}
}
