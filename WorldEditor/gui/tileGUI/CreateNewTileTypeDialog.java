package gui.tileGUI;

import java.awt.*;
import java.awt.event.*;
import classicUI.MessageDialog;
import tileSystem.*;

public class CreateNewTileTypeDialog extends Dialog
{
	static final long serialVersionUID = 3;
	
	TextField colorr;
	TextField colorg;
	TextField colorb;
	
	TextField name;
	TextField type;
	Button done;
	Choice impassable;
	
	CreateNewTileTypeDialog cnttd;
	TileSystem ts;
	
	public CreateNewTileTypeDialog(Frame owner, TileSystem tileSystem)
	{
		super(owner, "Create New Tile Type", true);
		cnttd = this;
		ts = tileSystem;
		
		Panel namep = new Panel();
		Label namel = new Label("Name:");
		name = new TextField(15);
		namep.add(namel);
		namep.add(name);
		
		Panel typep = new Panel();
		Label typel = new Label("Type:");
		type = new TextField(15);
		typep.add(typel);
		typep.add(type);
		
		Panel colorpr = new Panel();
		Panel colorpg = new Panel();
		Panel colorpb = new Panel();
		Label colorl = new Label("Color:");
		Label colorlr = new Label("Red:");
		Label colorlg = new Label("Green:");
		Label colorlb = new Label("Blue:");
		colorr = new TextField(15);
		colorg = new TextField(15);
		colorb = new TextField(15);
		//colorp.add(colorl);
		colorpr.add(colorlr);
		colorpr.add(colorr);
		colorpg.add(colorlg);
		colorpg.add(colorg);
		colorpb.add(colorlb);
		colorpb.add(colorb);
		
		Panel impassablep = new Panel();
		Label impassablel = new Label("Impassable");
		impassable = new Choice();
		impassable.addItem("false");
		impassable.addItem("true");
		impassablep.add(impassablel);
		impassablep.add(impassable);
		
		done = new Button("Done");
		done.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int typen = Integer.parseInt(type.getText());
					int red = Integer.parseInt(colorr.getText());
					int green = Integer.parseInt(colorg.getText());
					int blue = Integer.parseInt(colorb.getText());
					Color c = new Color(red, green, blue);
					boolean pass = false;
					if(impassable.getSelectedItem().equalsIgnoreCase("true"))
					{
						pass = true;
					}
					ts.registerTileType(new TileType(typen, name.getText(), c, pass));
					dispose();
				}
				catch(NumberFormatException a)
				{
					new MessageDialog(cnttd, "Number Format Exception", 170, 100);
				}
			}
		});
		Button cancel = new Button("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		
		setSize(207, 320);
		setResizable(false);
		setLayout(new FlowLayout());
		
		add(namep);
		add(typep);
		add(colorl);
		add(colorpr);
		add(colorpg);
		add(colorpb);
		add(impassablep);
		add(cancel);
		add(done);
		
		setVisible(true);
	}
}
