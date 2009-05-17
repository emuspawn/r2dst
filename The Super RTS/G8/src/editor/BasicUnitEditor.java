package editor;

import io.UnitReader;
import io.UnitWriter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;

import utilities.Location;
import world.unit.Unit;

/**
 * a basic unit editor
 * @author Jack
 *
 */
public class BasicUnitEditor extends JFrame
{
	JFrame f;
	JTextField name;
	JTextField life;
	JTextField movement;
	JTextField width;
	JTextField height;
	
	public BasicUnitEditor()
	{
		super("Basic Unit Editor");
		f = this;
		setSize(200, 230);
		
		add(createPanel());
		setJMenuBar(createMenuBar());
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args)
	{
		new BasicUnitEditor();
	}
	private JMenuBar createMenuBar()
	{
		JMenuBar b = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					System.out.println("saving unit...");
					String dir = System.getProperty("user.dir")+System.getProperty("file.separator")+
							"units"+System.getProperty("file.separator");
					System.out.println("dir = "+dir);
					File f = new File(dir+name.getText()+".unit");
					System.out.println("file name = "+f.getName());
					FileOutputStream fos = new FileOutputStream(f);
					DataOutputStream dos = new DataOutputStream(fos);
					UnitWriter.writeUnit(dos, name.getText(), Integer.parseInt(life.getText()), 
							Integer.parseInt(movement.getText()), Integer.parseInt(width.getText()),
							Integer.parseInt(height.getText()));
					System.out.println("done!");
				}
				catch(IOException a)
				{
					System.out.println("failed");
				}
			}
		});
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					String dir = System.getProperty("user.dir")+System.getProperty("file.separator")+
							"units"+System.getProperty("file.separator");
			
					File f = new File(dir+name.getText()+".unit");
					FileInputStream fis = new FileInputStream(f);
					DataInputStream dis = new DataInputStream(fis);
					
					Unit u = UnitReader.readUnit(dis, new Location(0, 0));
					name.setText(u.getName());
					life.setText(""+u.getLife());
					width.setText(""+u.getWidth());
					height.setText(""+u.getHeight());
				}
				catch(IOException a){}
			}
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		file.add(load);
		file.add(save);
		file.addSeparator();
		file.add(exit);
		b.add(file);
		return b;
	}
	private JPanel createPanel()
	{
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JLabel namel = new JLabel("Name:");
		name = new JTextField(15);
		JLabel lifel = new JLabel("Life:");
		life = new JTextField(15);
		JLabel movementl = new JLabel("Movement:");
		movement = new JTextField(15);
		JLabel widthl = new JLabel("Width:");
		width = new JTextField(15);
		JLabel heightl = new JLabel("Height:");
		height = new JTextField(15);
		p.add(namel);
		p.add(name);
		p.add(lifel);
		p.add(life);
		p.add(movementl);
		p.add(movement);
		p.add(widthl);
		p.add(width);
		p.add(heightl);
		p.add(height);
		return p;
	}
}
