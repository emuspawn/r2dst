package world.owner;

import java.awt.Color;

import sgEngine.EngineConstants;

import ai.AI;

public class Owner
{
	String name;
	Color c;
	int population = 0;
	
	/**
	 * the AI of the owner, if null the owner does nothing
	 */
	AI ai;
	
	double metal = EngineConstants.startingMetal;
	double energy = EngineConstants.startingEnergy;
	
	public Owner(String name, Color c)
	{
		this.c = c;
		this.name = name;
	}
	/**
	 * gets the total population value of the units commanded
	 * by this owner
	 * @return returns this owner's population
	 */
	public int getPopulation()
	{
		return population;
	}
	/**
	 * sets the owner's population value
	 * @param setter the new value of this owner's population
	 */
	public void setPopulation(int setter)
	{
		population = setter;
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
	/**
	 * returns the energy this owner has
	 * @return
	 */
	public double getEnergy()
	{
		return energy;
	}
	/**
	 * returns the metal this player has
	 * @return
	 */
	public double getMetal()
	{
		return metal;
	}
	/**
	 * sets the amount of energy this player has
	 * @param setter
	 */
	public void setEnergy(double setter)
	{
		energy = setter;
	}
	/**
	 * sets the amount of metal this player has
	 * @param setter
	 */
	public void setMetal(double setter)
	{
		metal = setter;
	}
}
