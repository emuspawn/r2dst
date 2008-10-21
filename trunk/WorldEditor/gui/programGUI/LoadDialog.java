package gui.programGUI;

import java.awt.event.*;
import java.awt.*;
import rpgWorld.loader.*;
import tileSystem.TileSystem;
import driver.MainThread;

public class LoadDialog extends Dialog
{
	final static long serialVersionUID = 4;
	TextField tf;
	TileSystem ts;
	MainThread mt;
	
	Dialog d;
	
	public LoadDialog(Frame owner, TileSystem t, MainThread m)
	{
		super(owner, "Load World", true);
		ts = t;
		mt = m;
		d = this;
		Button cancel = new Button("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		Button load = new Button("Load");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				LoadingDisplay ld = new LoadingDisplay(d);
				WorldReader.loadWorld(tf.getText(), ts, mt);
				ld.dispose();
				dispose();
			}
		});
		tf = new TextField(15);
		Label l = new Label("File:");
		setSize(200, 100);
		setLayout(new FlowLayout());
		add(l);
		add(tf);
		add(load);
		add(cancel);
		setResizable(false);
		setVisible(true);
	}
}
class LoadingDisplay extends Dialog
{
	final static long serialVersionUID = 5;
	public LoadingDisplay(Dialog owner)
	{
		super(owner, "");
		Label l = new Label("Loading...");
		setSize(100, 50);
		add(l);
		setVisible(true);
	}
}
