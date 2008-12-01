import owner.Owner;
import pathFinder.PathFinder;
import pathFinder.pathFinders.*;
import world.BuildEngineOverlay;
import driver.GameEngineOverlay;
import ai.AI;
import java.util.ArrayList;
import world.unit.*;
import utilities.Location;
import java.util.Random;


/* An Example AI
 * commented by kyle, made by jack
 *
 * - This AI can only play as Red in 
 * the top left corner of a 1000x1000
 * and is meant to be an example to 
 * learn from and improved on to make 
 * better AIs.
 *
 * It is very position and map dependent, 
 * a bad thing for an AI.
 * 
 * Your own AI should be able to beat 
 * this one at first without much trouble.
*/

public class BasicAIRed extends AI
{
	//Some constants, used to set limits on units produced
	int maxWorkers = 15;
	int maxFighters = 15;
	int maxHQs = 4;
	int maxBarracks = 3;
	int attackSize = 10;
	//For random numbers
	//to generate a number, do r.nextInt(x) where it will be a random number between 0 and x-1
	Random r = new Random();	

	/*Some useful functions
	 * -geo.getMapWidth() returns map width
	 * -geo.getMapHeight() returns map height
	 * 
	 * Some points are passed as Locations, use like this:
	 * Location l = new Location(x,y);
	 * l.x, l.y are coordinates of the location
	 * a random location inside the map is then this:
	 * l = new Location(r.nextInt(geo.getMapWidth(), geo.getMapHeight()));
	 *
	 * l is then a random point on the map :)
	 */

	//Constructor, leave this alone unless you need to do things only specific to your ai startup
	public BasicAIRed(Owner o, GameEngineOverlay geo, BuildEngineOverlay beo, PathFinder pf)
	{
		super(o, geo, beo, new DirectMovePF(geo.getMapWidth(), geo.getMapHeight()));
	}

	//This is the Ai's main method, which it needs to have, where everything is done
	public void performAIFunctions()
	{
		//lists of your units and visible resources
		ArrayList<FriendlyUnitMask> u = geo.getFriendlyUnits(o);
		ArrayList<Location> l = geo.getVisibleResources(o);
		
		//variables used to tally up your numbers of different types of units
		int workers = 0;
		int fighters = 0;
		int hqs = 0;
		int barracks = 0;

		//preset list of where to put hqs
		Location[] hql = new Location[3];
		hql[0] = new Location(500, 500);
		hql[1] = new Location(900, 100);
		hql[2] = new Location(100, 900);

		//This tallies up the types of units
		for(int i = 0; i < u.size(); i++)
		{
			if(u.get(i).getName().equalsIgnoreCase("worker1"))
			{
				workers++;
			}
			else if(u.get(i).getName().equalsIgnoreCase("fighter1"))
			{
				fighters++;
			}
			else if(u.get(i).getName().equalsIgnoreCase("hq1"))
			{
				hqs++;
			}
			else if(u.get(i).getName().equalsIgnoreCase("barracks1"))
			{
				barracks++;
			}
		}

		//Unit behaviors
		for(int i = 0; i < u.size(); i++)
		{
			//Worker actions
			if(u.get(i).getName().equalsIgnoreCase("worker1"))
			{
				boolean done = false;

				//This builds buildings, and gathers resources if it doesnt have enough to build
				if(hqs < maxHQs)
				{
					if(hqs < maxHQs && hqs > 0)
					{
						done = buildAt("hq1", u.get(i), hql[hqs-1]);
					}
					else if(hqs == 0)
					{
						done = buildAt("hq1", u.get(i), hql[0]);
					}
				}
				if(barracks < maxBarracks && !done)
				{
					done = build("barracks1", u.get(i));
				}
				if(!done)
				{
					gatherClosestResource(u.get(i), l);
				}
			}
			else if(u.get(i).getName().equalsIgnoreCase("fighter1"))
			{	
				//If it has more than the required number, attack
				//otherwise, they just sit there
				if(fighters >= attackSize)
				{
					Random r = new Random();
					int x = r.nextInt(100)+850;
					int y = r.nextInt(100)+850;
					moveUnit(u.get(i), new Location(x, y));
				}
			}
			//These checks build workers and fighters until they reach the specified number
			else if(u.get(i).getName().equalsIgnoreCase("hq1"))
			{
				if(workers < maxWorkers)
				{
					build("worker1", u.get(i));
				}
			}
			else if(u.get(i).getName().equalsIgnoreCase("barracks1"))
			{
				if(fighters < maxFighters)
				{
					build("fighter1", u.get(i));
				}
			}
		}
	}
}
