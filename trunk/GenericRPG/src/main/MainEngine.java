package main;

import java.awt.*;
import javax.swing.*;
import display.Drawer;
import player.*;
import units.UnitEngine;
import units.unit.Peasant;
import weapons.ShotEngine;

public class MainEngine implements Runnable
{	
	Drawer d;
	JFrame jf;
	Graphics g;
	Player p;
	KeyHandler kh;
	UnitEngine ue;
	ShotEngine se;
	
	public MainEngine()
	{
		p = new Player();
		se = new ShotEngine();
		ue = new UnitEngine(se);
		d = new Drawer(p, ue, se);
		kh = new KeyHandler(p, se);

		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(d, BorderLayout.CENTER);

		jf = new JFrame("GUI Test");
		jf.setContentPane(content);
		jf.addKeyListener(kh);
		jf.setSize(800, 600);
		jf.setLocation(100, 100);
		jf.setVisible(true);
		
		g = jf.getGraphics();
		
		new Thread(this).start();
	}
	
	public static void main(String[] args)
	{
		new MainEngine();
	}

	public void run()
	{
		ue.addUnit(new Peasant(100,100,10,0));
		ue.addUnit(new Peasant(200,350,15,0));
		ue.addUnit(new Peasant(500,500,10,3));
		
		for (;;)
		{
			kh.handleKeyPresses();

			ue.checkUnitCollisions();
			ue.performUnitActions();
			se.moveShots();
			
			jf.repaint();
			
			try
			{
				Thread.sleep(20);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
}