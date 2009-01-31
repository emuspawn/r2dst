package display;

import java.awt.*;
import javax.swing.*;
import owner.Owner;
import map.Map;
import shot.*;
import world.resource.Resource;
import world.World;
import world.unit.*;
import graphics.Camera;
import java.awt.event.*;
import listener.*;
import driver.GameEngine;
import java.util.LinkedList;
import java.util.ArrayList;

public class DrawFrame extends JFrame
{
	private final static long serialVersionUID = 4;
	
	DrawCanvas dc;
	KeyAction ka;
	GameEngine ge;
	JFrame owner;
	
	public DrawFrame(GameEngine gamee, World w, UnitEngine ue, ShotEngine se, Map m, Camera c)
	{
		super("G6");
		ge = gamee;
		ka = new KeyAction(ge, c);
		addKeyListener(ka);
		owner = this;
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		dc = new DrawCanvas(ge, w, ue, se, this, m, c);
		setSize(500, 500);
		
		JMenuBar mb = new JMenuBar();
		JMenu program = new JMenu("Program");
		JMenuItem setThreadSpeed = new JMenuItem("Set Game Thread Speed");
		setThreadSpeed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new SetThreadSpeedDialog(owner, ge);
			}
		});
		program.add(setThreadSpeed);
		
		mb.add(program);
		setJMenuBar(mb);
		
		add(dc);
		setVisible(true);
	}
	public DrawCanvas getDrawCanvas()
	{
		return dc;
	}
	public void displayWinScreen(String winner, String using)
	{
		new WinScreen(this, winner, using, ge);
	}
}
class WinScreen extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	GameEngine ge;
	JFrame owner;
	
	public WinScreen(JFrame o, String winnerName, String using, GameEngine gamee)
	{
		super(o, "Winner!", false);
		owner = o;
		setSize(280, 110);
		setLayout(new FlowLayout());
		JLabel winner = new JLabel(winnerName+"Wins!!! Using "+using);
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new GameEngine();
				owner.dispose();
			}
		});
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		add(winner);
		add(newGame);
		add(exit);
		setVisible(true);
	}
}
class SetThreadSpeedDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	JTextField tf;
	GameEngine ge;
	
	public SetThreadSpeedDialog(JFrame owner, GameEngine gamee)
	{
		super(owner, "Set Thread Speed", true);
		setSize(280, 105);
		setLayout(new FlowLayout());
		ge = gamee;
		JLabel l = new JLabel("Thread Speed:");
		JButton cancel = new JButton("Cancel");
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int speed = Integer.parseInt(tf.getText());
					if(speed < 1)
					{
						speed = 1;
					}
					ge.setThreadSpeed(speed);
					dispose();
				}
				catch(NumberFormatException a){}
			}
		});
		tf = new JTextField(15);
		add(l);
		add(tf);
		add(accept);
		add(cancel);
		setVisible(true);
	}
}
class DrawCanvas extends JPanel implements Runnable, FocusListener
{
	private final static long serialVersionUID = 4;
	
	World w;
	UnitEngine ue;
	ShotEngine se;
	Camera c;
	Frame owner;
	Map m;
	GameEngine ge;
	
	public DrawCanvas(GameEngine ge, World w, UnitEngine ue, ShotEngine se, Frame owner, Map m, Camera c)
	{
		setSize(owner.getWidth(), owner.getHeight());
		this.w = w;
		this.ue = ue;
		this.se = se;
		this.c = c;
		this.owner = owner;
		this.m = m;
		addFocusListener(this);
		this.ge = ge;
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	public void run()
	{
		for(;;)
		{
			repaint();
			try
			{
				Thread.sleep(50);
			}
			catch(InterruptedException e){}
		}
	}
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		Image i = createVolatileImage(getWidth(), getHeight());
		drawOffScreen((Graphics2D)i.getGraphics());
		g2.drawImage(i, 0, 0, null);
	}
	private void drawOffScreen(Graphics2D g)
	{
		g.setColor(Color.gray);
		g.fillRect(0, 0, getWidth(), getHeight());
		//ArrayList<Unit> u = ue.getUnits();
		LinkedList<Unit> u = ue.getUnits();
		for(int i = 0; i < u.size(); i++)
		{
			u.get(i).drawUnit(g, c);
		}
		g.setColor(Color.black);
		g.drawRect(0-c.getxover(), 0-c.getyover(), m.getMapWidth(), m.getMapHeight());
		ArrayList<Resource> r = w.getResources();
		for(int i = 0; i < r.size(); i++)
		{
			r.get(i).drawResource(g, c);
		}
		LinkedList<Shot> s = se.getShots();
		for(int i = 0; i < s.size(); i++)
		{
			s.get(i).drawShot(g, c);
		}
		
		g.setColor(Color.cyan);
		Point p = c.getScreenLocation(new Point(m.getMapWidth()/2-10, m.getMapHeight()/2-10));
		g.fillRect(p.x, p.y, 20, 20);
		
		g.setColor(Color.black);
		ArrayList<Owner> o = ge.getOwners();
		if(o != null)
		{
			for(int i = o.size()-1; i >= 0; i--)
			{
				g.drawString(o.get(i).getName()+": "+o.get(i).getResourceCount("green circle")+
						",  pop: ("+o.get(i).getUnitCount()+" / "+o.get(i).getCurrentUnitMax()+
						")", 5, (i*17)+17);
			}
		}
	}
	public void focusGained(FocusEvent e)
	{
		owner.requestFocus();
	}
	public void focusLost(FocusEvent e){}
}
