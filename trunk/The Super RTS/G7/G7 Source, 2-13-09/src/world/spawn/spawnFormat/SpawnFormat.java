package world.spawn.spawnFormat;

import world.Element;
import world.resource.Resource;
import world.World;
import java.util.Random;
import utilities.Location;

/*
 * how Element 'e' spawns other elements, repawns the given element
 * when counter reaches counterMax, respawns in the region defined
 * by width and height centered on the element's location
 * 
 * diff is how much counterMax can vary from the given value
 */

public abstract class SpawnFormat
{
	protected Element e;
	int counter = 0;
	int width;
	int height;
	int counterMax;
	
	public SpawnFormat(Element e, int width, int height, int counterMax, int diff)
	{
		this.e = e;
		this.width = width;
		this.height = height;
		calculateCounterMax(diff);
	}
	private void calculateCounterMax(int diff)
	{
		int sign = new Random().nextInt(1);
		if(sign == 0)
		{
			this.counterMax = counterMax-new Random().nextInt(diff);
		}
		else
		{
			this.counterMax = counterMax+new Random().nextInt(diff);
		}
	}
	protected abstract Resource spawnResource(Location l);
	public void updateCounter(World w)
	{
		counter++;
		if(counter >= counterMax)
		{
			Random r = new Random();
			int x = r.nextInt(width)+(int)e.getLocation().x-width/2;
			int y = r.nextInt(height)+(int)e.getLocation().y-height/2;
			w.registerElement(spawnResource(new Location(x, y)));
		}
	}
}
