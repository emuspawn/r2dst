package customAI;

import world.owner.Owner;
import pathFinder.PathFinder;
import world.BuildEngineOverlay;
import world.WorldOverlay;
import ai.AI;
import java.util.ArrayList;
import world.unit.FriendlyUnitMask;
import utilities.Location;

public final class TestAI2 extends AI
{
	public TestAI2(Owner o, WorldOverlay wo, BuildEngineOverlay beo, PathFinder pf)
	{
		super(o, wo, beo, pf);
	}
	public void performAIFunctions()
	{
		ArrayList<FriendlyUnitMask> u = wo.getFriendlyUnits(o);
		for(int i = 0; i < u.size(); i++)
		{
			moveUnit(u.get(i), new Location(700, 700));
		}
	}
}
