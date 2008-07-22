package world.unit.unitMover;

import utilities.Location;
import world.unit.*;

public class MoverseV2 extends UnitMover
{
	public MoverseV2()
	{
		
	}
	public void moveUnits(Unit[] units)
	{
		Unit[] u = units;
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] != null)
			{
				if(u[i].getMoving())
				{
					moveUnit(u[i]);
				}
			}
		}
	}
	private void moveUnit(Unit u)
	{
		boolean doneMoving = false;
		double dt = 0; //distance traveled
		
		//System.out.println("unit moving");
		
		while(!doneMoving)
		{
			if(u.getPointMovingTo() == null)
			{
				u.setPointMovingTo(u.getPath().getNextLocation());
				
				if(u.getPointMovingTo() != null)
				{
					//System.out.println("unit got next location, ("+u.getPointMovingTo().x+", "+u.getPointMovingTo().y+")");
				}
				
				if(u.getPointMovingTo() == null)
				{
					//the unit has arrived at the end of its path
					
					//System.out.println("unit arrived at the end of its path");
					
					doneMoving = true;
					u.setMoving(false);
				}
			}
			if(!doneMoving)
			{
//				checks to see if the unit is in range of the point its moving to
				double term1 = u.getLocation().x - u.getPointMovingTo().x;
				double term2 = u.getLocation().y - u.getPointMovingTo().y;
				double dtnp = Math.sqrt((term1*term1)+(term2*term2)); //distance to next point
				
				//System.out.println("distance to next point = "+dtnp);
				
				if(dtnp <= u.getMovement()-dt)
				{
					//unit in range of its next point
					
					//System.out.println("next point inside unit movement range");
					
					u.setLocation(u.getPointMovingTo());
					u.setPointMovingTo(null);
					dt += dtnp;
					
					//System.out.println("distance traveled = "+dt);
				}
				else
				{
					//unit not in range of its next point and will move as close as possible
					
					//System.out.println("next point not inside unit movement range");
					
					//double xover = u.getPointMovingTo().x - u.getLocation().x;
					//double yover = u.getPointMovingTo().y - u.getLocation().y;
					
					double xover = Math.abs(u.getPointMovingTo().x - u.getLocation().x);
					double yover = Math.abs(u.getPointMovingTo().y - u.getLocation().y);
					
					//System.out.println("unit location = ("+u.getLocation().x+", "+u.getLocation().y+")");
					//System.out.println("point moving to = ("+u.getPointMovingTo().x+", "+u.getPointMovingTo().y+")");
					
					//System.out.println("xover = "+xover);
					//System.out.println("yover = "+yover);

					double angle = Math.atan(xover/yover);
					double x = Math.cos(angle)*(u.getMovement()-dt);
					double y = Math.sin(angle)*(u.getMovement()-dt);
					
					if(xover == 0)
					{
						y = u.getMovement()-dt;
					}
					else if(yover == 0)
					{
						x = u.getMovement()-dt;
					}
					
					//System.out.println("angle = "+angle);
					//System.out.println("x plus amount = "+x);
					//System.out.println("y plus amount = "+y);
					
					if(u.getLocation().x < u.getPointMovingTo().x)
					{
						if(u.getLocation().y < u.getPointMovingTo().y)
						{
							//right, up
							u.setLocation(new Location(u.getLocation().x+x, u.getLocation().y+y));
							//System.out.println("moved unit, right up");
						}
						else if(u.getLocation().y > u.getPointMovingTo().y)
						{
							//right, down
							u.setLocation(new Location(u.getLocation().x+x, u.getLocation().y-y));
							//System.out.println("moved unit, right down");
						}
						else if(u.getLocation().y == u.getPointMovingTo().y)
						{
							//right
							u.setLocation(new Location(u.getLocation().x+x, u.getLocation().y));
							//System.out.println("moved unit, right");
						}
					}
					else if(u.getLocation().x > u.getPointMovingTo().x)
					{
						if(u.getLocation().y < u.getPointMovingTo().y)
						{
							//left, up
							u.setLocation(new Location(u.getLocation().x-x, u.getLocation().y+y));
							//System.out.println("moved unit, left up");
						}
						else if(u.getLocation().y > u.getPointMovingTo().y)
						{
							//left, down
							u.setLocation(new Location(u.getLocation().x-x, u.getLocation().y-y));
							//System.out.println("moved unit, left down");
						}
						else if(u.getLocation().y == u.getPointMovingTo().y)
						{
							//left
							u.setLocation(new Location(u.getLocation().x-x, u.getLocation().y));
							//System.out.println("moved unit, left");
						}
					}
					else if(u.getLocation().x == u.getPointMovingTo().x)
					{
						if(u.getLocation().y < u.getPointMovingTo().y)
						{
							//up
							u.setLocation(new Location(u.getLocation().x, u.getLocation().y+y));
							//System.out.println("moved unit, up");
						}
						else if(u.getLocation().y > u.getPointMovingTo().y)
						{
							//down
							u.setLocation(new Location(u.getLocation().x, u.getLocation().y-y));
							//System.out.println("moved unit, down");
						}
					}
					//System.out.println("unit location set to ("+u.getLocation().x+", "+u.getLocation().y+")");
					
					dt = u.getMovement();
				}
				if(dt >= u.getMovement())
				{
					doneMoving = true;
				}
			}
		}
		//System.out.println();
		//System.out.println("unit move order completed");
		//System.out.println();
	}
}
