package gui.worldGUI;

import java.awt.*;
import tileSystem.*;
import driver.MainThread;
import java.awt.event.*;

public class SetDimensionDialog extends Dialog
{
	static final long serialVersionUID = 7;
	TextField width;
	TextField height;
	MainThread mt;
	
	public SetDimensionDialog(Frame owner, MainThread mainThread)
	{
		super(owner, "Set Dimensions", true);
		mt = mainThread;
		Label w = new Label("Width:");
		Label h = new Label("Height:");
		width = new TextField(""+mt.getMapWidth(), 15);
		height = new TextField(""+mt.getMapHeight(), 15);
		Button accept = new Button("Accept");
		accept.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int wn = Integer.parseInt(width.getText());
					int hn = Integer.parseInt(height.getText());
					mt.setMapWidth(wn);
					mt.setMapHeight(hn);
					Tile[] t = mt.getTileSystem().getTiles();
					mt.getTileSystem().setupTileSelectionMatrix(wn, hn);
					mt.getTileSystem().registerTiles(t);
					dispose();
				}
				catch(NumberFormatException a)
				{
					
				}
			}
		});
		setSize(150, 200);
		setLayout(new FlowLayout());
		add(w);
		add(width);
		add(h);
		add(height);
		add(accept);
		setResizable(false);
		setVisible(true);
	}
}
