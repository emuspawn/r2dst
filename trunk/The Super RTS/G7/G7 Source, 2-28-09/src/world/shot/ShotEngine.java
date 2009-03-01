package world.shot;

import java.util.ArrayList;
import utilities.Location;
import world.unit.*;

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
	public void performShotFunctions()
	{
		//ArrayList<Unit> u = ue.getUnits();
		ArrayList<Unit> u = ue.getUnits();
		Location l;
		for(int i = u.size()-1; i >= 0; i--)
		{
			for(int a = s.size()-1; a >= 0; a--)
			{
				l = s.get(a).start;
				if(!s.get(a).dead && u.get(i).getBounds().contains(l.x, l.y) && !u.get(i).getOwner().getName().equalsIgnoreCase(s.get(a).getOwner().getName()))
				{
					u.get(i).setLife(u.get(i).getLife()-s.get(a).getDamage());
					s.get(a).dead = true;
					
					srbuc++;
				}
			}
			for(int a = u.size()-1; a >= 0; a--)
			{
				if(a != i)
				{
					if(u.get(i).getDamage() > 0)
					{
						if(!u.get(i).getWeaponFired())
						{
							if(!u.get(a).getOwner().getName().equalsIgnoreCase("neutral"))
							{
								if(!u.get(a).getOwner().getName().equalsIgnoreCase(u.get(i).getOwner().getName()))
								{
									if(u.get(i).getLocation().distanceTo(u.get(a).getLocation()) <= u.get(i).getRange())
									{
										u.get(i).fireWeapon();
										s.add(new Shot(u.get(i).getOwner(), u.get(i).getLocation(), u.get(a).getLocation(), u.get(i).getDamage()));
										shotsFired++;
									}
								}
							}
						}
						else
						{
							//System.out.println("weapon fired = false");
							u.get(i).updateShotCount();
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
	public ArrayList<Shot> getShots()
	{
		return s;
	}
}
