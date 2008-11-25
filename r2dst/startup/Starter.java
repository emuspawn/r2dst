package startup;

import java.io.*;
import superIO.CustomClassLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import ai.*;
import java.util.ArrayList;
import owner.Owner;
import pathFinder.PathFinder;
import driver.GameEngineOverlay;
import world.BuildEngineOverlay;

public class Starter extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	File f;
	String[] files;
	JFrame frame;
	JMenuBar jmb = new JMenuBar();
	JTextField mwidth;
	JTextField mheight;
	
	JList ailist1;
	JList ailist2;
	
	CustomClassLoader ccl1;
	CustomClassLoader ccl2;
	
	ArrayList<AI> ais1 = new ArrayList<AI>();
	ArrayList<AI> ais2 = new ArrayList<AI>();
	
	Owner owner1;
	Owner owner2;
	GameEngineOverlay geo;
	BuildEngineOverlay beo;
	PathFinder pf;
	
	Thread t;
	
	public Starter(Thread t, Owner owner1, Owner owner2, GameEngineOverlay geo, BuildEngineOverlay beo, PathFinder pf)
	{
		super("AI Selector");
		setSize(300, 280);
		setLayout(new FlowLayout());
		String dir = System.getProperty("user.dir")+System.getProperty("file.separator")+"customAI";
		ccl1 = new CustomClassLoader(dir);
		ccl2 = new CustomClassLoader(dir);
		f = new File(dir);
		files = f.list();
		frame = this;
		this.t = t;
		
		this.owner1 = owner1;
		this.owner2 = owner2;
		this.geo = geo;
		this.beo = beo;
		this.pf = pf;
		
		loadAI(owner1, ais1, ccl1);
		System.out.println("DONE LOADING PLAYER 1's AIs");
		loadAI(owner2, ais2, ccl2);
		System.out.println("DONE LOADING PLAYER 2's AIs");
		
		//setupLoadAIPart();
		//setupAIDisplayPanel();
		setupAISelectStart();
		setupMapMenu();
		
		setJMenuBar(jmb);
		setVisible(true);
	}
	private void setupMapMenu()
	{
		JMenu map = new JMenu("Map");
		JMenuItem dim = new JMenuItem("Set Map Dimensions");
		
		map.add(dim);
		jmb.add(map);
	}
	private void setupAISelectStart()
	{
		ailist1 = setupList(ailist1);
		ailist2 = setupList(ailist2);
		ailist1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane ai1 = new JScrollPane(ailist1);
		JScrollPane ai2 = new JScrollPane(ailist2);
		JButton start = new JButton("Start!");
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(ailist1.getSelectedIndex() != -1 && ailist2.getSelectedIndex() != -1)
				{
					new AIThread(ais1.get(ailist1.getSelectedIndex()));
					owner1.setAIUsing(ailist1.getSelectedValue().toString());
					new AIThread(ais2.get(ailist2.getSelectedIndex()));
					owner2.setAIUsing(ailist2.getSelectedValue().toString());
					t.start();
				}
			}
		});
		add(ai1);
		add(ai2);
		add(start);
	}
	private JList setupList(JList l)
	{
		DefaultListModel dlm = new DefaultListModel();
		for(int i = 0; i < files.length; i++)
		{
			if(files[i].endsWith(".class"))
			{
				dlm.addElement(files[i]);
			}
		}
		l = new JList(dlm);
		l.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return l;
	}
	private void loadAI(Owner o, ArrayList<AI> ais, CustomClassLoader ccl)
	{
		for(int i = 0; i < files.length; i++)
		{
			if(files[i].endsWith(".class"))
			{
				try
				{
					System.out.println("loading "+files[i]);
					Class c = ccl.loadClass(files[i]);
					Class[] args = new Class[4];
					args[0] = o.getClass();
					args[1] = geo.getClass();
					args[2] = beo.getClass();
					args[3] = pf.getClass().getSuperclass();
					//args[3] = null;
					ais.add((AI)ccl.constructObject(c, args, o, geo, beo, pf));
				}
				catch(ClassNotFoundException a)
				{
					a.printStackTrace();
				}
			}
		}
	}
}
