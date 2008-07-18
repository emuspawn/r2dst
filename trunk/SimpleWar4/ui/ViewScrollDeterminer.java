package ui;

import graphics.Camera;
import java.awt.Point;

public class ViewScrollDeterminer
{
	int screenWidth;
	int screenHeight;
	Camera c;
	int allowance = 10; //how far from the edge of the screen before scrolling
	int over = 10; //how far over to move the screen
	
	public ViewScrollDeterminer(Camera c, int screenWidth, int screenHeight)
	{
		this.c = c;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	public void testForScreenScroll(Point p)
	{
		if(p.x <= allowance)
		{
			c.setxover(c.getxover()-over);
		}
		else if(p.x >= screenWidth-allowance)
		{
			c.setxover(c.getxover()+over);
		}
		else if(p.y <= allowance)
		{
			c.setyover(c.getyover()-over);
		}
		else if(p.y >= screenHeight-allowance)
		{
			c.setyover(c.getyover()+over);
		}
	}
}
