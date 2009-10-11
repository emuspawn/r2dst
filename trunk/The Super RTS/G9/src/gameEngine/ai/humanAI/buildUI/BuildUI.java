package gameEngine.ai.humanAI.buildUI;

import java.util.HashSet;

import javax.media.opengl.GL;

import utilities.Region;

/**
 * diplays the possible build choices (depending on the highlighted units),
 * initiates build orders when displayed choices are clicked
 * @author Jack
 *
 */
public class BuildUI
{
	/**
	 * draws the build ui
	 * @param gl
	 * @param x the x location of the bottom left coord of the UI
	 * @param y the y location of the bottom left coord of the UI
	 * @param width the width of the displayed region
	 * @param buildableUnit a hash set representing the units that can be
	 * built by one or more of the currently selected units
	 */
	public void drawBuildUI(GL gl, double x, double y, int width, HashSet<String> buildableUnits)
	{
		
		new Region(x, y, width, buildableUnits.size()*20).fillRegion(gl);
	}
}
