package editor.mapEditor;

import ui.menu.*;
import ui.UICheckEngine;
import editor.mapEditor.tile.*;
import editor.mapEditor.listener.*;
import editor.mapEditor.gui.*;
import controller.MapEditorController;
import graphics.*;
import java.awt.Point;
import editor.mapEditor.saver.*;
import ui.inputBox.*;

public class MapEditor implements MapEditorController, Runnable
{
	KeyAction ka;
	MouseAction ma;
	MouseMotionAction mma;
	
	GraphicsFinder gf;
	MapEditorDrawer med;
	Tile[] t = new Tile[20];
	int gridWidth = 30;
	int gridHeight = 30;
	Camera c;
	Point ml = new Point(200, 200); //mouse location
	int mapWidth = 500;
	int mapHeight = 500;
	String mapName = new String("Untitled");
	MapScribe ms;
	MapScribbler msb;
	UICheckEngine uice;
	
	MenuCheckEngine mce;
	InputBoxCheckEngine ibce;
	
	boolean leftDown = false; //left mouse button down
	boolean rightDown = false;
	boolean sni = false; //save next iteration
	boolean spni = false; //save pic next iteration
	
	int editType;
	
	/*
	 * edit type:
	 * 1=wall
	 */
	
	public MapEditor()
	{
		ka = new KeyAction(this);
		ma = new MouseAction(this);
		mma = new MouseMotionAction(this);
		
		mce = new MenuCheckEngine(this, new Register());
		ibce = new InputBoxCheckEngine(this, new BoxRegister());
		uice = new UICheckEngine(mce, ibce);
		
		gf = new GraphicsFinder(ma, ka, mma);
		c = new Camera(gf.getWidth(), gf.getHeight());
		med = new MapEditorDrawer(this, gf);
		mma.passCamera(c);
		ms = new MapScribe(this);
		msb = new MapScribbler(this);
		
		Thread runner = new Thread(this);
		runner.start();
	}
	public boolean getSavingPicNextIteration()
	{
		return spni;
	}
	public void setMapName(String s)
	{
		mapName = s;
	}
	public void setMapWidth(int setter)
	{
		mapWidth = setter;
	}
	public void setMapHeight(int setter)
	{
		mapHeight = setter;
	}
	public UICheckEngine getUICheckEngine()
	{
		return uice;
	}
	public void setSavePicNextIteration()
	{
		spni = true;
	}
	public MapEditorDrawer getMapEditorDrawer()
	{
		return med;
	}
	public void saveNextIteration()
	{
		sni = true;
	}
	public void savePicNextIteration()
	{
		spni = true;
	}
	public String getMapName()
	{
		return mapName;
	}
	public void setLeftDown(boolean setter)
	{
		leftDown = setter;
	}
	public boolean getLeftDown()
	{
		return leftDown;
	}
	public void setRightDown(boolean setter)
	{
		rightDown = setter;
	}
	public boolean getRightDown()
	{
		return rightDown;
	}
	public Camera getCamera()
	{
		return c;
	}
	public int getMapWidth()
	{
		return mapWidth;
	}
	public int getMapHeight()
	{
		return mapHeight;
	}
	public void setMouseLocation(Point p)
	{
		ml = p;
	}
	public int getEditType()
	{
		return editType;
	}
	public Tile[] getTiles()
	{
		return t;
	}
	public int getGridWidth()
	{
		return gridWidth;
	}
	public void setGridWidth(int setter)
	{
		gridWidth = setter;
	}
	public int getGridHeight()
	{
		return gridHeight;
	}
	public void setGridHeight(int setter)
	{
		gridHeight = setter;
	}
	public void addTile(Tile tile)
	{
		boolean added = false;
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] == null)
			{
				t[i] = tile;
				added = true;
				break;
			}
		}
		if(!added)
		{
			enlargeTileArray();
			addTile(tile);
		}
	}
	private void enlargeTileArray()
	{
		Tile[] temp = new Tile[t.length+1];
		for(int i = 0; i < t.length; i++)
		{
			temp[i] = t[i];
		}
		t = temp;
	}
	public GraphicsFinder getGraphicsFinder()
	{
		return gf;
	}
	public boolean getSavingNextIteration()
	{
		return sni;
	}
	public void run()
	{
		for(;;)
		{
			uice.performUIFunctions();
			med.performDrawFunctions();
			moveScreen();
			testForMapSave();
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException e){}
		}
	}
	private void testForMapSave()
	{
		if(sni)
		{
			ms.saveMap();
			sni = false;
		}
		else if(spni)
		{
			msb.saveMapPicture();
			spni = false;
		}
	}
	private void moveScreen()
	{
		int xl = 5; //x leeway
		int yl = 5; //yleeway
		int movement = 7;
		if(ml.x < xl)
		{
			c.translate(-movement, 0);
		}
		else if(ml.x > gf.getWidth()-xl)
		{
			c.translate(movement, 0);
		}
		if(ml.y < yl)
		{
			c.translate(0, -movement);
		}
		else if(ml.y > gf.getHeight()-yl)
		{
			c.translate(0, movement);
		}
	}
	public static void main(String[] args)
	{
		new MapEditor();
	}
	public void setEditType(int setter)
	{
		editType = setter;
	}
}
