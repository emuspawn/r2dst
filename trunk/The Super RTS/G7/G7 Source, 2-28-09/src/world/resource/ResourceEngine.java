package world.resource;

import java.util.ArrayList;
import world.World;

public class ResourceEngine
{
	ArrayList<Resource> r = new ArrayList<Resource>();
	World w;
	
	public ResourceEngine(World w)
	{
		this.w = w;
	}
	public void performResourceFunctions()
	{
		for(int i = r.size()-1; i >= 0; i--)
		{
			if(r.get(i).isDead())
			{
				r.remove(i);
			}
			else
			{
				r.get(i).updateSpawnCounter(w);
			}
		}
	}
	public void registerResource(Resource resource)
	{
		r.add(resource);
	}
	public ArrayList<Resource> getResources()
	{
		return r;
	}
}
