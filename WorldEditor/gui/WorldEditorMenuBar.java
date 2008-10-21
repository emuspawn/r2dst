package gui;

import java.awt.*;
//import rpgWorld.saver.WorldWriter;
//import rpgWorld.loader.WorldReader;
import gui.programGUI.*;
import gui.worldGUI.*;
import java.awt.event.*;
import gui.tileGUI.*;
import tileSystem.TileSystem;
import driver.MainThread;

public class WorldEditorMenuBar extends MenuBar
{
	static final long serialVersionUID = 3;
	Frame owner;
	TileSystem ts;
	MainThread mt;
	
	public WorldEditorMenuBar(Frame ownerFrame, MainThread mainThread, TileSystem tileSystem)
	{
		owner = ownerFrame;
		ts = tileSystem;
		mt = mainThread;
		//tile menu
		Menu tile = new Menu("Tile");
		MenuItem createNewTileType = new MenuItem("Create New Tile Type...");
		createNewTileType.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new CreateNewTileTypeDialog(owner, ts);
			}
		});
		tile.add(createNewTileType);
		tile.addSeparator();
		MenuItem openTileSelector = new MenuItem("Open Tile Selector");
		openTileSelector.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new TileSelectorDialog(owner, mt, ts);
			}
		});
		tile.add(openTileSelector);
		
		//world menu
		Menu world = new Menu("World");
		MenuItem setDim = new MenuItem("Set Dimensions");
		setDim.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new SetDimensionDialog(owner, mt);
			}
		});
		world.add(setDim);
		
		//program menu
		Menu program = new Menu("Program");
		MenuItem save = new MenuItem("Save World");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				//WorldWriter.saveWorld(mt.getMapName(), mt.getTileSystem(), mt);
				new SaveDialog(owner, ts, mt);
			}
		});
		MenuItem load = new MenuItem("Load World");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				//WorldReader.loadWorld(mt.getMapName(), ts, mt);
				new LoadDialog(owner, ts, mt);
			}
		});
		program.add(save);
		program.add(load);
		program.addSeparator();
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		program.add(exit);
		
		//edit type menu
		Menu editType = new Menu("Edit Type");
		MenuItem terrain = new MenuItem("Edit Terrain");
		MenuItem roofs = new MenuItem("Edit Roofs");
		terrain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				mt.setEditType(1);
			}
		});
		roofs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				mt.setEditType(2);
			}
		});
		
		add(program);
		add(editType);
		add(world);
		add(tile);
	}
}
