package editor.gameEditor;

import io.ShotWriter;
import io.WeaponWriter;
import java.awt.event.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import world.shot.Shot;

/**
 * an encompassing editor for all game elements
 * @author Jack
 *
 */
public class GameEditor extends JFrame
{
	JFrame owner;
	
	JTable unitTable;
	JTable weaponTable;
	JTable shotTable;
	
	public GameEditor()
	{
		super("Game Editor");
		owner = this;
		JTabbedPane tpane = new JTabbedPane();
		tpane.addTab("Units", createUnitPanel());
		
		tpane.addTab("Weapons", createWeaponTable());
		
		tpane.addTab("Shots", createShotPanel());
		add(tpane);
		
		setJMenuBar(createJMenuBar());
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				new ExitDialog(owner);
			}
		});
		
		setSize(1000, 600);
		setVisible(true);
	}
	private JMenuBar createJMenuBar()
	{
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu("File");
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				ArrayList<Shot> shots = writeShots();
				writeWeapons(shots);
			}
		});
		JMenuItem load = new JMenuItem("Load");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new ExitDialog(owner);
			}
		});
		file.add(save);
		file.add(load);
		file.addSeparator();
		file.add(exit);
		
		mb.add(file);
		
		return mb;
	}
	/**
	 * writes the shot data
	 */
	private ArrayList<Shot> writeShots()
	{
		System.out.println("saving...");
		System.out.println("reading shot table data...");
		ArrayList<Shot> shots = new ArrayList<Shot>();
		for(int r = 0; r < shotTable.getRowCount(); r++)
		{
			String name = (String)shotTable.getValueAt(r, 0);
			String damage = (String)shotTable.getValueAt(r, 1);
			String movement = (String)shotTable.getValueAt(r, 2);
			String width = (String)shotTable.getValueAt(r, 3);
			String height = (String)shotTable.getValueAt(r, 4);
			String depth = (String)shotTable.getValueAt(r, 5);
			if(name != null && damage != null && movement != null
					&& width != null && height != null && depth != null)
			{
				System.out.println(name+", dmg="+damage+", mov="+movement+", w="+
						width+", h="+height+", d="+depth);
				try
				{
					double d = Double.parseDouble(damage);
					double m = Double.parseDouble(movement);
					double w = Double.parseDouble(width);
					double h = Double.parseDouble(height);
					double de = Double.parseDouble(depth);
					shots.add(new Shot(name, d, m, w, h, de));
				}
				catch(NumberFormatException a)
				{
					System.out.println("shot: \""+name+"\" data read incorrectly, syntax errors exist in entry");
					new ArgumentProblemDialog(owner, "\""+name+"\" data has syntax errors, data not saved");
				}
			}
		}
		System.out.println("done");
		System.out.println("saving shot data...");
		Iterator<Shot> si = shots.iterator();
		while(si.hasNext())
		{
			Shot s = si.next();
			try
			{
				System.out.print("saving "+s.getName()+"... ");
				String dir = System.getProperty("user.dir")+System.getProperty("file.separator")+
						"shots"+System.getProperty("file.separator");
				System.out.println("dir = "+dir);
				File f = new File(dir+s.getName()+".shot");
				System.out.print("file name = "+f.getName()+", ");
				FileOutputStream fos = new FileOutputStream(f);
				DataOutputStream dos = new DataOutputStream(fos);
				ShotWriter.writeShot(dos, s);
				System.out.println(" done!");
			}
			catch(IOException a)
			{
				System.out.println("failed, io exception");
				new ArgumentProblemDialog(owner, s.getName()+" write failed, data not saved");
			}
		}
		System.out.println("done");
		return shots;
	}
	public static void main(String[] args)
	{
		new GameEditor();
	}
	private JScrollPane createUnitPanel()
	{
		String[] columnNames = {"Name", "Weapon", "Life", "Movement", "Energy Cost", 
				"Metal Cost", "Energy Drain", "Metal Drain", "Energy Storage", 
				"Metal Storage", "Width", "Height", "Depth", "Build Time", "Build Tree"};
		unitTable = new JTable(new DefaultTableModel(columnNames, 30));
		unitTable.setFillsViewportHeight(true);
		JScrollPane sp = new JScrollPane(unitTable);
		return sp;
	}
	private JScrollPane createWeaponTable()
	{
		String[] columnNames = {"Name", "Shot Type", "Range", "Reload"};
		weaponTable = new JTable(new DefaultTableModel(columnNames, 15));
		weaponTable.setFillsViewportHeight(true);
		JScrollPane sp = new JScrollPane(weaponTable);
		return sp;
	}
	private JScrollPane createShotPanel()
	{
		String[] columnNames = {"Name", "Damage", "Movement", "Width", "Height", "Depth"};
		shotTable = new JTable(new DefaultTableModel(columnNames, 15));
		shotTable.setFillsViewportHeight(true);
		JScrollPane sp = new JScrollPane(shotTable);
		return sp;
	}
	/**
	 * writes the weapon data
	 */
	private void writeWeapons(ArrayList<Shot> shots)
	{
		System.out.println("saving...");
		System.out.println("reading weapon table data...");
		for(int r = 0; r < weaponTable.getRowCount(); r++)
		{
			String name = (String)weaponTable.getValueAt(r, 0);
			String shotType = (String)weaponTable.getValueAt(r, 1);
			String range = (String)weaponTable.getValueAt(r, 2);
			String reload = (String)weaponTable.getValueAt(r, 3);
			if(name != null && shotType != null && range != null && reload != null)
			{
				System.out.println(name+", stype="+shotType+", rng="+range+", rload="+reload);
				try
				{
					double ra = Double.parseDouble(range);
					int re = Integer.parseInt(reload);
					boolean contains = false;
					Iterator<Shot> si = shots.iterator();
					while(si.hasNext() && !contains)
					{
						if(si.next().getName().equalsIgnoreCase(shotType))
						{
							contains = true;
						}
					}
					if(contains)
					{
						try
						{
							System.out.print("saving "+name+"... ");
							String dir = System.getProperty("user.dir")+System.getProperty("file.separator")+
									"weapons"+System.getProperty("file.separator");
							System.out.println("dir = "+dir);
							File f = new File(dir+name+".weapon");
							System.out.print("file name = "+f.getName()+", ");
							FileOutputStream fos = new FileOutputStream(f);
							DataOutputStream dos = new DataOutputStream(fos);
							WeaponWriter.writeWeapon(dos, name, shotType, ra, re);
							System.out.println(" done!");
						}
						catch(IOException a)
						{
							System.out.println("failed, io exception");
							new ArgumentProblemDialog(owner, name+" write failed, data not saved");
						}
					}
					else
					{
						System.out.println("weapon: \""+name+"\" entered shot type not recognized, not saved");
						new ArgumentProblemDialog(owner, "\""+name+"\" shot type does not exist");
					}
				}
				catch(NumberFormatException a)
				{
					System.out.println("weapon: \""+name+"\" data read incorrectly, syntax errors exist in entry");
					new ArgumentProblemDialog(owner, "\""+name+"\" data has syntax errors, data not saved");
				}
			}
		}
		System.out.println("done");
	}
}
class ExitDialog extends JDialog
{
	public ExitDialog(JFrame owner)
	{
		super(owner, "Exit Editor", true);
		setSize(300, 100);
		setLocationRelativeTo(owner);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		setLayout(new FlowLayout());
		add(new JLabel("Are you sure you want to exit?"));
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(exit);
		p.add(cancel);
		add(p);

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
}
/**
 * created when there are problems with the arguments that a user has put in
 * @author Jack
 *
 */
final class ArgumentProblemDialog extends JDialog
{
	/**
	 * creates a new argument problem dialog
	 * @param owner the owner of the dialog
	 * @param problem the problem that has occured
	 */
	public ArgumentProblemDialog(JFrame owner, String problem)
	{
		super(owner, "Argument Problem", true);
		setSize(300, 100);
		setLocationRelativeTo(owner);
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(new JLabel("There are problems with the enertered arguments:"));
		p.add(new JLabel(problem));
		JButton accept = new JButton("Accept");
		p.add(accept);
		accept.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		add(p);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
}
