package editor.mapEditor;

import io.*;
import java.io.*;
import javax.swing.*;
import utilities.Location;
import java.util.ArrayList;
import world.*;
import graphics.Camera;
import java.awt.*;
import java.awt.event.*;
import factory.TerrainFactory;

/**
 * edits map files for use in the game
 * @author Jack
 *
 */

public final class MapEditor extends JFrame implements KeyListener, Runnable, FocusListener
{
	Camera c;
	DrawCanvas dc;
	int fps = 0; //frames per second
	World w;
	MapEditor owner;
	String mapName = new String("untitled");
	String title = new String("Map Editor V2 - ");
	String description = new String("");
	ArrayList<Location> startLocations = new ArrayList<Location>();
	boolean drawGrid = true;
	boolean placingStartLocation = false;
	
	JSplitPane sp;
	JList terrain; //the terrain selection
	SelectionPanel selectionPanel;
	
	boolean ud = false; //up down
	boolean rd = false;
	boolean dd = false;
	boolean ld = false;
	
	boolean saved = false;
	
	/**
	 * creats a new map editor
	 */
	public MapEditor()
	{
		super("Map Editor V2 - untitled");
		w = new World(1000, 1000);
		owner = this;
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		c = new Camera(0, 0);
		setSize(500, 500);
		dc = new DrawCanvas(this, c, w);
		
		
		//sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dc, setupTerrainList());
		//dc.setTerrainSelectionList(terrain);
		selectionPanel = new SelectionPanel();
		sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dc, selectionPanel);
		
		sp.setDividerLocation(400);
		//add(dc);
		add(sp);
		setJMenuBar(setupMenuBar());
		
		addKeyListener(this);
		addFocusListener(this);
		
		setVisible(true);
		new Thread(this).start();
	}
	public SelectionPanel getSelectionPanel()
	{
		return selectionPanel;
	}
	public ArrayList<Location> getStartLocations()
	{
		return startLocations;
	}
	public String getMapDescription()
	{
		return description;
	}
	public String getTitleName()
	{
		return title;
	}
	public void setSaved(boolean setter)
	{
		saved = setter;
	}
	/**
	 * gets the map name
	 * @return returns the map name
	 */
	public String getMapName()
	{
		return mapName;
	}
	/**
	 * sets the map name
	 * @param s the new map name
	 */
	public void setMapName(String s)
	{
		mapName = s;
	}
	public void setWorld(World w)
	{
		this.w = w;
		dc.w = this.w;
	}
	/**
	 * 
	 * @return returns the current fps
	 */
	public int getFPS()
	{
		return fps;
	}
	private JMenuBar setupMenuBar()
	{
		JMenuBar mb = new JMenuBar();

		JMenu editor = new JMenu("Editor");
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(!saved)
				{
					new SaveAsDialog(owner, w);
				}
				else
				{
					try
					{
						String ud = System.getProperty("user.dir");
						File f = new File(ud+"\\"+mapName+".wrld");
						FileOutputStream fos = new FileOutputStream(f);
						DataOutputStream dos = new DataOutputStream(fos);
						MapWriter.writeMap(mapName, description, startLocations, w, dos);
					}
					catch(IOException a)
					{
						System.out.println("io exception");
						a.printStackTrace();
					}
				}
			}
		});
		JMenuItem saveAs = new JMenuItem("Save As...");
		saveAs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				saved = true;
				new SaveAsDialog(owner, w);
			}
		});
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new LoadMapDialog(owner, w);
				Location[] l = w.getStartLocations();
				for(int i = l.length-1; i >= 0; i--)
				{
					startLocations.add(l[i]);
				}
			}
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		editor.add(save);
		editor.add(saveAs);
		editor.addSeparator();
		editor.add(load);
		editor.addSeparator();
		editor.add(exit);
		
		JMenu view = new JMenu("View");
		JMenuItem toggleGrid = new JMenuItem("Toggle Grid");
		toggleGrid.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				drawGrid = !drawGrid;
			}
		});
		view.add(toggleGrid);
		
		JMenu world = new JMenu("World");
		JMenuItem setDim = new JMenuItem("Set World Dimensions");
		setDim.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new AdjustMapDimDialog(owner, w);
			}
		});
		JMenuItem placeStartLocation = new JMenuItem("Place Start Location");
		placeStartLocation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				placingStartLocation = true;
			}
		});
		JMenuItem removeStartLocation = new JMenuItem("Remove Start Location");
		removeStartLocation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new RemoveStartLocationDialog(owner);
			}
		});
		world.add(setDim);
		world.addSeparator();
		world.add(placeStartLocation);
		world.add(removeStartLocation);
		
		mb.add(editor);
		mb.add(view);
		mb.add(world);
		
		return mb;
	}
	private JPanel setupTerrainList()
	{
		JPanel p = new JPanel();
		DefaultListModel dlm = new DefaultListModel();
		dlm.addElement("Hard Rock");
		dlm.addElement("Water");
		dlm.addElement("Tree");
		
		terrain = new JList(dlm);
		terrain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane t = new JScrollPane(terrain);
		p.setLayout(new BorderLayout());
		p.add(t);
		
		return p;
	}
	public static void main(String[] args)
	{
		new MapEditor();
	}
	public void run()
	{
		requestFocus();
		double start;
		double end;
		for(;;)
		{
			
			start = System.currentTimeMillis();
			dc.repaint();
			adjustCamera();
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException e){}
			end = System.currentTimeMillis();
			
			//System.out.println(1/((end-start)/1000)+" operations a second");
			fps = (int)(1/((end-start)/1000));
		}
	}
	public void focusLost(FocusEvent e)
	{
		requestFocus();
	}
	public void focusGained(FocusEvent e){}
	private void adjustCamera()
	{
		if(ud)
		{
			c.translate(0, -10);
		}
		if(rd)
		{
			c.translate(10, 0);
		}
		if(dd)
		{
			c.translate(0, 10);
		}
		if(ld)
		{
			c.translate(-10, 0);
		}
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			ud = true;
		}
		else if(e.getKeyChar() == 'd')
		{
			rd = true;
		}
		else if(e.getKeyChar() == 's')
		{
			dd = true;
		}
		else if(e.getKeyChar() == 'a')
		{
			ld = true;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyChar() == 'w')
		{
			ud = false;
		}
		else if(e.getKeyChar() == 'd')
		{
			rd = false;
		}
		else if(e.getKeyChar() == 's')
		{
			dd = false;
		}
		else if(e.getKeyChar() == 'a')
		{
			ld = false;
		}
	}
	public void keyTyped(KeyEvent e){}
}
class LoadMapDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	JTextField tf;
	World w;
	MapEditor me;

	public LoadMapDialog(MapEditor owner, World world)
	{
		super(owner, "Load...", true);
		w = world;
		me = owner;
		tf = new JTextField(15);
		JButton load = new JButton("Load");
		JButton cancel = new JButton("Cancel");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					String ud = System.getProperty("user.dir");
					File f = new File(ud+"\\"+tf.getText()+".wrld");
					FileInputStream fis = new FileInputStream(f);
					DataInputStream dis = new DataInputStream(fis);
					MapReader.readMap(w, dis);
					me.setSaved(true);
					me.setMapName(tf.getText());
					me.setTitle(me.getTitleName()+me.getMapName());
					dispose();
				}
				catch(IOException a)
				{
					System.out.println("io exception");
					a.printStackTrace();
				}
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		setLayout(new FlowLayout());
		setSize(230, 100);
		add(tf);
		add(load);
		add(cancel);
		setVisible(true);
	}

	public void update(Graphics g)
	{
		paint(g);
	}
}
class SaveAsDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	JTextField tf;
	World w;
	MapEditor me;

	public SaveAsDialog(MapEditor owner, World world)
	{
		super(owner, "Save As...", true);
		me = owner;
		w = world;
		tf = new JTextField(me.getMapName(), 15);
		tf.selectAll();
		JButton save = new JButton("Save");
		JButton cancel = new JButton("Cancel");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					String ud = System.getProperty("user.dir");
					File f = new File(ud+"\\"+tf.getText()+".wrld");
					FileOutputStream fos = new FileOutputStream(f);
					DataOutputStream dos = new DataOutputStream(fos);
					MapWriter.writeMap(me.getMapName(), me.getMapDescription(), me.getStartLocations(), w, dos);
					me.setSaved(true);
					me.setMapName(tf.getText());
					me.setTitle(me.getTitleName()+me.getMapName());
					dispose();
				}
				catch(IOException a)
				{
					System.out.println("io exception");
					a.printStackTrace();
				}
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		setLayout(new FlowLayout());
		setSize(230, 100);
		add(tf);
		add(save);
		add(cancel);
		setVisible(true);
	}
}
class AdjustMapDimDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	World world;
	JTextField width;
	JTextField height;
	
	public AdjustMapDimDialog(JFrame owner, World wrld)
	{
		super(owner, "World Dimensions", true);
		world = wrld;
		width = new JTextField(world.getWidth()+"", 10);
		height = new JTextField(world.getHeight()+"", 10);
		setSize(190, 160);
		setLayout(new FlowLayout());
		JPanel w = new JPanel();
		w.add(new JLabel("Width:"));
		w.add(width);
		JPanel h = new JPanel();
		h.add(new JLabel("Height:"));
		h.add(height);
		add(w);
		add(h);
		JPanel p = new JPanel();
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int w = Integer.parseInt(width.getText());
					int h = Integer.parseInt(height.getText());
					world.getDynamicMap().setSize(w, h);
					dispose();
				}
				catch(NumberFormatException a)
				{
					System.out.println("number format exception");
				}
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		p.add(accept);
		p.add(cancel);
		add(p);
		setVisible(true);
	}
}
class RemoveStartLocationDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	MapEditor me;
	JTextField tf;

	public RemoveStartLocationDialog(MapEditor owner)
	{
		super(owner, "Remove Start Location", true);
		me = owner;
		setSize(180, 130);
		setLayout(new FlowLayout());
		tf = new JTextField(10);
		JButton accept = new JButton("Done");
		JButton cancel = new JButton("Cancel");
		accept.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int index = Integer.parseInt(tf.getText());
					me.startLocations.remove(index);
					dispose();
				}
				catch(NumberFormatException a){}
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		add(new JLabel("Start Location Index:"));
		add(tf);
		add(accept);
		add(cancel);
		setVisible(true);
	}
}