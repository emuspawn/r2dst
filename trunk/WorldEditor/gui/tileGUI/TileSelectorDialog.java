package gui.tileGUI;

import java.awt.*;
import tileSystem.*;
import driver.MainThread;
import java.awt.event.*;
import classicUI.MessageDialog;

/*
 * selects the tile type to be used when adding tiles
 * to the world
 */

public class TileSelectorDialog extends Dialog
{
	static final long serialVersionUID = 5;
	TileSystem ts;
	
	Panel lp;
	Label l; //displayes the currently selected tile type
	
	TextArea ta;
	TextField tf; //input for new tile type
	
	TileSelectorDialog tsd;
	
	public TileSelectorDialog(Frame owner, MainThread mt, TileSystem tileSystem)
	{
		super(owner, "Tile Selector");
		ts = tileSystem;
		tsd = this;
		setSize(450, 450);
		setLayout(new FlowLayout());
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		});
		
		setupDialog();
	}
	private void setupDialog()
	{
		Button close = new Button("Close");
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});

		ta = new TextArea("", 20, 60, TextArea.SCROLLBARS_BOTH);
		
		//appends tile types to the text area
		TileType[] tt = ts.getTileTypeRegistry().getTileTypes();
		for(int i = 0; i < tt.length; i++)
		{
			if(tt[i] != null)
			{
				ta.append("Name: "+tt[i].getName()+"\tType: "+tt[i].getType()+"\t  Color: r="+
						tt[i].getColor().getRed()+", g="+tt[i].getColor().getGreen()+", b="+
						tt[i].getColor().getBlue()+"\tImpassable: "+tt[i].getImpassable()+"\n");
			}
		}
		
		Button refresh = new Button("Refresh");
		refresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				setupDialog();
			}
		});
		
//		panel for selecting the tile type
		Panel selectp = new Panel();
		Label tfl = new Label("Input Type:");
		tf = new TextField(15);
		Button select = new Button("Select");
		select.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int type = Integer.parseInt(tf.getText());
					ts.setSelectedTileType(type);
					System.out.println(ts.getSelectedTileType());
					
					l = new Label("Current Tile Type: "+ts.getSelectedTileType());
					setupDialog();
				}
				catch(NumberFormatException a)
				{
					new MessageDialog(tsd, "Number Format Exception", 170, 100);
				}
			}
		});
		selectp.add(tfl);
		selectp.add(tf);
		selectp.add(select);
		
		lp = new Panel();
		lp.setBackground(Color.lightGray);
		l = new Label("Current Tile Type: "+ts.getSelectedTileType());
		lp.add(l);
		
		removeAll();
		add(lp);
		add(selectp);
		add(ta);
		add(refresh);
		add(close);
		
		setResizable(false);
		setVisible(true);
	}
}