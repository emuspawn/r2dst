package rpgWorld.unit;

public class UnitEngine
{
	Unit[] u = new Unit[1];
	Unit[] hero = new Unit[1]; //index-1 is that players here, ex player 3 hero is hero[2]
	
	public UnitEngine()
	{
		
	}
	public Unit[] getUnits()
	{
		return u;
	}
	public void registerUnit(Unit unit)
	{
		boolean added = false;
		for(int i = 0; i < u.length; i++)
		{
			if(u[i] == null)
			{
				u[i] = unit;
				added = true;
				break;
			}
		}
		if(!added)
		{
			enlargeUnitArray();
			registerUnit(unit);
		}
	}
	private void enlargeUnitArray()
	{
		Unit[] temp = new Unit[u.length+1];
		for(int i = 0; i < u.length; i++)
		{
			temp[i] = u[i];
		}
		u = temp;
	}
	public Unit getHero(int player)
	{
		return hero[player-1];
	}
}
