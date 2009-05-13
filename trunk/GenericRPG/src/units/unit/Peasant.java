package units.unit;
import units.Unit;


public class Peasant extends Unit
{
	static int iters = 0;
	int choice = r.nextInt(4);
	
	public Peasant(int x, int y, int maxLife, int maxMana)
	{
		super(x, y, 10, 3, 20);
		moveSpeed = 3;
	}

	public void act()
	{
		if (x < 0)
			choice = 1;
		if (x > 760)
			choice = 3;
		if (y < 0)
			choice = 0;
		if (y > 560)
			choice = 2;
		
		if (iters%20 == 0)
			choice = r.nextInt(4);
		
		if (choice == 0)
			move(0, moveSpeed);
		if (choice == 1)
			move(moveSpeed, 0);
		if (choice == 2)
			move(0, -moveSpeed);
		if (choice == 3)
			move(-moveSpeed, 0);
		iters++;
	}
}
