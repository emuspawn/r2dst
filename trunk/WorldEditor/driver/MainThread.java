package driver;

import controller.WorldEditorController;
import tileSystem.*;
import listener.*;
import graphics.*;
import drawing.*;
import java.awt.Color;

public class MainThread implements WorldEditorController, Runnable
{
	KeyAction ka;
	MouseAction ma;
	MouseMotionAction mma;
	GraphicsFinder gf;
	Camera c;

	TileSystem ts;
	
	Drawer d;
	DrawFrame df;
	DrawData dd;
	
	int mapWidth = 700; //the width of the world
	int mapHeight = 700;
	int editType = 1;
	
	public MainThread()
	{
		dd = new DrawData();
		
		ka = new KeyAction(this);
		ma = new MouseAction(this);
		mma = new MouseMotionAction(this);
		//gf = new GraphicsFinder(ma, ka, mma);
		
		ts = new TileSystem(mapWidth, mapHeight);
		ts.registerTileType(new TileType(1, "Wall", Color.black, false));
		//ts.setSelectedTileType(1);
		
		c = new Camera(200, 200);
		d = new Drawer(this, c, dd, ts);
		df = new DrawFrame(d, this, ts);
		//df.addKeyListener(ka);
		//df.addMouseListener(ma);
		//df.addMouseMotionListener(mma);
		df.getDrawCanvas().addKeyListener(ka);
		df.getDrawCanvas().addMouseListener(ma);
		df.getDrawCanvas().addMouseMotionListener(mma);
		df.setVisible(true);
		
		Thread runner = new Thread(this);
		runner.start();
	}
	public void setMapWidth(int setter)
	{
		mapWidth = 1000;
	}
	public int getMapWidth()
	{
		return mapWidth;
	}
	public void setMapHeight(int setter)
	{
		mapHeight = setter;
	}
	public int getMapHeight()
	{
		return mapHeight;
	}
	public static void main(String[] args)
	{
		new MainThread();
	}
	public DrawData getDrawData()
	{
		return dd;
	}
	public void run()
	{
		for(;;)
		{
			c.setWidth(df.getDrawCanvas().getWidth());
			c.setHeight(df.getDrawCanvas().getHeight());
			df.repaintCanvas();
			df.getDrawCanvas().setSize(df.getWidth(), df.getHeight());
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException e){}
		}
	}
	public Camera getCamera()
	{
		return c;
	}
	public int getEditType()
	{
		return editType;
	}
	public TileSystem getTileSystem()
	{
		return ts;
	}
	public void setEditType(int setter)
	{
		editType = setter;
	}
}
