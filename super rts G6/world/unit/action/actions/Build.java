package world.unit.action.actions;

import driver.GameOverlay;
import world.unit.action.*;
import world.unit.Unit;
import world.unit.FriendlyUnitMask;

/*
 * builds a unit or building, sends the build requests to the build engine overlay
 * and starts a counter, when the counter reaches the build time the unit is then
 * declared to be idle
 */

public class Build extends Action
{
	int counter = 0;
	int maxCounter;
	GameOverlay go;
	FriendlyUnitMask building;
	Unit u;
	boolean constructionStarted = false;
	
	public Build(GameOverlay go, FriendlyUnitMask building, Unit u)
	{
		super("Build");
		this.go = go;
		this.building = building;
		maxCounter = u.getBuildTime();
		this.u = u;
	}
	public boolean performAction()
	{
		if(!constructionStarted)
		{
			constructionStarted = true;
			go.startUnitConstruction(building.getOwner(), u);
		}
		counter++;
		if(counter == maxCounter)
		{
			return true;
		}
		return false;
	}
}
