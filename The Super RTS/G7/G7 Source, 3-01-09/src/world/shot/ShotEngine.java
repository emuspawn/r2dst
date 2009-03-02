package world.shot;

import java.util.ArrayList;
import utilities.Location;
import world.unit.*;

/**
 * manages the firing of all weapons and the shots from
 * those weapons
 * @author Jack
 *
 */
public class ShotEngine
{
	ArrayList<Shot> s = new ArrayList<Shot>();
	UnitEngine ue;
	
	int shotsFired = 0;
	int shotsRemoved = 0;
	int srbuc = 0; //shots removed by unit collision
	int srbus = 0; //shots removed by update shots
	
	public ShotEngine(UnitEngine ue)
	{
		this.ue = ue;
	}
	public ArrayList<Shot> getShots()
	{
		return s;
	}
	public void performShotFunctions()
	{
		ArrayList<Unit> u = ue.getUnits();
		Location l;
		for(int i = u.size()-1; i >= 0; i--)
		{
			for(int a = s.size()-1; a >= 0; a--)
			{
				l = s.get(a).start;
				//if(!s.get(a).dead && u.get(i).getBounds().contains(l.x, l.y) && !u.get(i).getOwner().getName().equalsIgnoreCase(s.get(a).getOwner().getName()))
				if(!s.get(a).dead && u.get(i).getBounds().intersects(s.get(a).getBounds()) && !u.get(i).getOwner().getName().equalsIgnoreCase(s.get(a).getOwner().getName()))
				{
					u.get(i).setLife(u.get(i).getLife()-s.get(a).getDamage());
					s.get(a).dead = true;
					
					srbuc++;
				}
			}
			double distance;
			for(int a = u.size()-1; a >= 0; a--)
			{
				if(a != i)
				{
					if(u.get(i).getWeapon() != null)
					{
						if(!u.get(i).getWeapon().isFired())
						{
							if(!u.get(a).getOwner().getName().equalsIgnoreCase("neutral"))
							{
								if(!u.get(a).getOwner().getName().equalsIgnoreCase(u.get(i).getOwner().getName()))
								{
									distance = u.get(i).getLocation().distanceTo(u.get(a).getLocation());
									if(distance <= u.get(i).getViewRange())
									{
										//inside view range
										if(distance <= u.get(i).getWeapon().getRange())
										{
											//inside weapon range
											u.get(i).getWeapon().fireWeapon();
											s.add(u.get(i).getWeapon().getShot(u.get(i).getOwner(), u.get(i).getLocation(), u.get(a).getLocation()));
											shotsFired++;
										}
									}
								}
							}
						}
						else
						{
							u.get(i).getWeapon().updateShotCount();
						}
					}
				}
			}
		}
		for(int i = s.size()-1; i >= 0; i--)
		{
			s.get(i).updateShot();
			if(s.get(i).dead)
			{
				s.remove(i);
				
				srbus++;
				shotsRemoved++;
			}
		}
	}
}
