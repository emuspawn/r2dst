package world.spawn;

import java.util.ArrayList;
import world.World;

public class SpawnEngine
{
	World w;
	ArrayList<Spawner> s = new ArrayList<Spawner>();
	
	public SpawnEngine(World w)
	{
		this.w = w;
	}
	public void registerSpawner(Spawner spawner)
	{
		s.add(spawner);
	}
	public void performSpawnEngineFunctions()
	{
		for(int i = s.size()-1; i >= 0; i--)
		{
			s.get(i).updateCounter(w);
		}
	}
}
