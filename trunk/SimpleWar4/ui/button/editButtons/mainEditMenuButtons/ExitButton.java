package ui.button.editButtons.mainEditMenuButtons;

import ui.button.*;
import world.World;

public class ExitButton extends Button
{
	public ExitButton(int x, int y, int width, int height)
	{
		super("Exit", x, y, width, height);
	}
	public void performAction(World w)
	{
		System.exit(0);
	}
}
