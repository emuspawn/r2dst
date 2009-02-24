package display.worldDisplay;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import listener.*;
import world.World;

public class DrawFrame extends JFrame
{
	private final static long serialVersionUID = 4;
	
	DrawCanvas dc;
	KeyAction ka;
	JFrame owner;
	World w;
	
	public DrawFrame(World world)
	{
		super("G7");
		owner = this;
		w = world;
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		dc = new DrawCanvas(w, this);
		ka = new KeyAction(dc);
		addKeyListener(ka);
		setSize(500, 500);
		
		JMenuBar mb = new JMenuBar();
		JMenu program = new JMenu("Program");
		JMenuItem setThreadSpeed = new JMenuItem("Set Game Thread Speed");
		setThreadSpeed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new SetThreadSpeedDialog(owner, w);
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
		new WinScreen(this, winner, using, w);
	}
}
class WinScreen extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	JFrame owner;
	World w;
	
	public WinScreen(JFrame o, String winnerName, String using, World w)
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
	World w;
	
	public SetThreadSpeedDialog(JFrame owner, World world)
	{
		super(owner, "Set Thread Speed", true);
		setSize(280, 105);
		setLayout(new FlowLayout());
		w = world;
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
					w.setThreadSpeed(speed);
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
