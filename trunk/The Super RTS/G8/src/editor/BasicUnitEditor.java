package editor;

import io.UnitWriter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import javax.swing.*;
import sgEngine.EngineConstants;
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
	JTextField weapon;
	JTextField life;
	JTextField movement;
	JTextField width;
	JTextField height;
	JTextField depth;
	
	JTextField energyCost;
	JTextField metalCost;
	JTextField energyDrain;
	JTextField metalDrain;
	
	JTextArea buildTree;
	JTextField buildTime;
	
	public BasicUnitEditor()
	{
		super("Basic Unit Editor");
		f = this;
		setSize(200, 380);
		
		JTabbedPane sp = new JTabbedPane();
		sp.addTab("General", null, createGeneralPanel(), "Edits the general stats of the unit");
		sp.addTab("Resource", null, createResourcePanel(), "Edits the resource aspects of the unit");
		
		
		sp.addTab("Build Tree", null, createBuildTreePanel(), "Edits the build tree of the unit");
		
		add(sp);
		setJMenuBar(createMenuBar());
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private JPanel createBuildTreePanel()
	{
		JPanel p = createPanel();
		buildTree = new JTextArea();
		buildTree.setLineWrap(true);
		buildTree.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(buildTree);
		p.add(new JLabel("Build Tree:"));
		p.add(scroll);
		return p;
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
					String[] buildTree = getBuildTree();
					UnitWriter.writeUnit(dos, name.getText(), weapon.getText(), Double.parseDouble(life.getText()), 
							Double.parseDouble(movement.getText()), Double.parseDouble(energyCost.getText()), 
							Double.parseDouble(metalCost.getText()), Double.parseDouble(energyDrain.getText()), 
							Double.parseDouble(metalDrain.getText()), Double.parseDouble(width.getText()),
							Double.parseDouble(height.getText()), Double.parseDouble(depth.getText()),
							Integer.parseInt(buildTime.getText()), buildTree);
					System.out.println("done!");
				}
				catch(Exception a)
				{
					a.printStackTrace();
					System.out.println("failed to save");
				}
			}
		});
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Unit u = EngineConstants.unitFactory.makeUnit(name.getText(), null, null);
					weapon.setText(u.getWeapon().getName());
					life.setText(""+u.getLife());
					movement.setText(""+u.getMovement());
					
					energyCost.setText(""+u.getEnergyCost());
					metalCost.setText(""+u.getMetalCost());
					energyDrain.setText(""+u.getEnergyDrain());
					metalDrain.setText(""+u.getMetalDrain());
					
					width.setText(""+u.getWidth());
					height.setText(""+u.getHeight());
					depth.setText(""+u.getDepth());
					
					buildTime.setText(""+u.getBuildTime());
					
					String bt = "";
					Iterator<String> i = u.getBuildTree().iterator();
					while(i.hasNext())
					{
						bt+=i.next()+";";
					}
					buildTree.setText(bt);
				}
				catch(NullPointerException a)
				{
					System.out.println("incorrect name, unit load failed");
				}
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
	/**
	 * interprets the build tree text area to determine the proper build tree
	 * @return
	 */
	private String[] getBuildTree()
	{
		String bt = buildTree.getText();
		String[] s = bt.split(";");
		for(int i = 0; i < s.length; i++)
		{
			s[i] = s[i].trim();
		}
		return s;
	}
	private JPanel createGeneralPanel()
	{
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JLabel namel = new JLabel("Name:");
		name = new JTextField(15);
		JLabel weaponl = new JLabel("Weapon:");
		weapon = new JTextField(15);
		JLabel lifel = new JLabel("Life:");
		life = new JTextField(15);
		JLabel movementl = new JLabel("Movement:");
		movement = new JTextField(15);
		JLabel widthl = new JLabel("Width:");
		width = new JTextField(15);
		JLabel heightl = new JLabel("Height:");
		height = new JTextField(15);
		depth = new JTextField(15);
		p.add(namel);
		p.add(name);
		p.add(weaponl);
		p.add(weapon);
		p.add(lifel);
		p.add(life);
		p.add(movementl);
		p.add(movement);
		p.add(widthl);
		p.add(width);
		p.add(heightl);
		p.add(height);
		p.add(new JLabel("Depth:"));
		p.add(depth);
		return p;
	}
	private JPanel createResourcePanel()
	{
		JPanel p = createPanel();
		energyCost = new JTextField(15);
		metalCost = new JTextField(15);
		energyDrain = new JTextField(15);
		metalDrain = new JTextField(15);
		buildTime = new JTextField(15);
		p.add(new JLabel("Energy Cost:"));
		p.add(energyCost);
		p.add(new JLabel("Metal Cost:"));
		p.add(metalCost);
		p.add(new JLabel("Energy Drain:"));
		p.add(energyDrain);
		p.add(new JLabel("Metal Drain:"));
		p.add(metalDrain);
		p.add(new JLabel("Build Time:"));
		p.add(buildTime);
		return p;
	}
	private JPanel createPanel()
	{
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		return p;
	}
}
