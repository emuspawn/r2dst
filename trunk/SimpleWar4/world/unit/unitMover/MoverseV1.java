package world.unit.unitMover;

import utilities.Location;
import world.unit.*;

public class MoverseV1 extends UnitMover
{
	public MoverseV1(){}
	public void moveUnits(Unit[] u)
	{
		double distTraveled;
		double xdist;
		double ydist;
		boolean inRange = false; //whether or not the point moving to next can be reached in one movement phase
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				//System.out.println("u["+i+"] location = "+u[i].getLocation().x+", "+u[i].getLocation().y);
				if(u[i].getMoving())
				{
					//System.out.println("u["+i+"] location (moving) = "+u[i].getLocation().x+", "+u[i].getLocation().y);
					//System.out.println();
					//System.out.println("moving unit "+i);
					
					distTraveled = u[i].getMovement();
					
					
					int whileloopiterations = 0;
					
					
					while(distTraveled > 0)
					{
						whileloopiterations++;
						//System.out.println("loop iterations, "+whileloopiterations);
						
						//System.out.println("dist left to travel = "+distTraveled);
						
						
						inRange = false;
						if(u[i].getPointMovingTo() == null)
						{
							u[i].setPointMovingTo(u[i].getPath().getNextLocation());
						}
						xdist = Math.abs(u[i].getLocation().x - u[i].getPointMovingTo().x);
						ydist = Math.abs(u[i].getLocation().y - u[i].getPointMovingTo().y);
						if(xdist == 0)
						{
							if(ydist <= distTraveled)
							{
								inRange = true;
							}
						}
						else
						{
							if(xdist <= distTraveled)
							{
								inRange = true;
							}
						}
						if(inRange)
						{
							//System.out.println("in range");
							//System.out.println("location set to "+u[i].getPointMovingTo().x+", "+u[i].getPointMovingTo().y);
							//System.out.println("or location at "+(u[i].getPointMovingTo().x*20)+", "+(u[i].getPointMovingTo().y*20));
							
							
							u[i].setLocation(u[i].getPointMovingTo());
							//u[i].setLocation(new Location(u[i].getPointMovingTo().x*20, u[i].getPointMovingTo().y*20));
							
							
							u[i].setPointMovingTo(u[i].getPath().getNextLocation());
							if(u[i].getPointMovingTo() == null)
							{
								u[i].setMoving(false);
								System.out.println("MOVING SET TO FALSE");
								System.out.println();
							}
							if(xdist == 0)
							{
								distTraveled -= ydist;
							}
							else
							{
								distTraveled -= xdist;
							}
						}
						else
						{
							xdist = u[i].getLocation().x - u[i].getPointMovingTo().x;
							ydist = u[i].getLocation().y - u[i].getPointMovingTo().y;
							
							//xdist = u[i].getLocation().x - u[i].getPointMovingTo().x*20;
							//ydist = u[i].getLocation().y - u[i].getPointMovingTo().y*20;
							
							
							if(xdist < 0)
							{
								//move right
								//System.out.println("right called");
								u[i].setLocation(new Location(u[i].getLocation().x + distTraveled, u[i].getLocation().y));
							}
							else if(xdist > 0)
							{
								//move left
								//System.out.println("left called");
								u[i].setLocation(new Location(u[i].getLocation().x - distTraveled, u[i].getLocation().y));
							}
							else if(ydist < 0)
							{
								//move down
								//System.out.println("down called");
								u[i].setLocation(new Location(u[i].getLocation().x, u[i].getLocation().y + distTraveled));
							}
							else if(ydist < 0)
							{
								//move up
								//System.out.println("up called");
								u[i].setLocation(new Location(u[i].getLocation().x, u[i].getLocation().y - distTraveled));
							}
							distTraveled = 0;
						}
					}
					
				}
			}
		}
	}
}
