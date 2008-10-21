package listener;

import driver.MainThread;
import java.awt.event.*;
import inputListener.MouseMotionActionListener;
import drawing.DrawData;

public class MouseMotionAction extends MouseMotionActionListener
{
	MainThread mt;
	DrawData dd;
	int tileWidth = 30;
	int tileHeight = 30;
	
	public MouseMotionAction(MainThread mt)
	{
		this.mt = mt;
		dd = mt.getDrawData();
	}
	public void mouseDragged(MouseEvent e)
	{
		if(e.getModifiers() == MouseEvent.BUTTON1_MASK)
		{
			int x = (e.getPoint().x+mt.getCamera().getxover())/dd.getGridSize()*tileWidth;
			int y = (e.getPoint().y+mt.getCamera().getyover())/dd.getGridSize()*tileHeight;
			if(x > -1 && x <= mt.getMapWidth() && y > -1 && y <= mt.getMapHeight())
			{
				//mt.getTileSystem().registerTile(new Tile(mt.getEditType(), x, y));
				mt.getTileSystem().registerTile(x, y);
			}
		}
		if(e.getModifiers() == MouseEvent.BUTTON3_MASK)
		{
			int x = (e.getPoint().x+mt.getCamera().getxover())/dd.getGridSize()*tileWidth;
			int y = (e.getPoint().y+mt.getCamera().getyover())/dd.getGridSize()*tileHeight;
			if(x > -1 && x <= mt.getMapWidth() && y > -1 && y <= mt.getMapHeight())
			{
				mt.getTileSystem().removeTile(x, y);
			}
		}
	}
}
