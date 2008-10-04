package gui;

import java.awt.*;
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
		
		add(tile);
	}
}
