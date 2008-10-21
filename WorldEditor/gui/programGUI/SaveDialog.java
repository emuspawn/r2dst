package gui.programGUI;

import java.awt.event.*;
import java.awt.*;
import rpgWorld.saver.WorldWriter;
import tileSystem.TileSystem;
import driver.MainThread;

public class SaveDialog extends Dialog
{
	final static long serialVersionUID = 4;
	TextField tf;
	TileSystem ts;
	MainThread mt;
	
	Dialog d;
	
	public SaveDialog(Frame owner, TileSystem t, MainThread m)
	{
		super(owner, "Save World", true);
		ts = t;
		mt = m;
		d = this;
		Label info = new Label("(Automatically adds .wrld)");
		Button cancel = new Button("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		Button save = new Button("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				SavingDisplay sd = new SavingDisplay(d);
				WorldWriter.saveWorld(tf.getText(), mt.getTileSystem(), mt);
				sd.dispose();
				dispose();
			}
		});
		tf = new TextField(15);
		Label l = new Label("File:");
		setSize(200, 130);
		setLayout(new FlowLayout());
		add(l);
		add(tf);
		add(save);
		add(cancel);
		add(info);
		setResizable(false);
		setVisible(true);
	}
}
class SavingDisplay extends Dialog
{
	final static long serialVersionUID = 5;
	public SavingDisplay(Dialog owner)
	{
		super(owner, "");
		Label l = new Label("Saving...");
		setSize(100, 50);
		add(l);
		setVisible(true);
	}
}
