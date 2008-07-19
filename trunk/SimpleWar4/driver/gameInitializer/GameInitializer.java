package driver.gameInitializer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameInitializer extends Frame
{
	public GameInitializer()
	{
		super("Simple War 4");
		
		setMenuBar(setupMenuBar());
		add(setupCanvas());
		
		setSize(510, 550);
		setVisible(true);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
	private Canvas setupCanvas()
	{
		Canvas c = new Canvas(){
			public void paint(Graphics g)
			{
				Image img = null;
				String userdir = System.getProperty("user.dir");
				File f = new File(userdir+"\\driver\\gameInitializer\\simpleWarIntroScreen.png");
				try {
				    img = ImageIO.read(f);
				    System.out.println("loaded intro pic");
				} catch (IOException e) {
					System.out.println("io exception in loading intro pic");
				}
				g.drawImage(img, 0, 0, null);
			}
		};
		return c;
	}
	public static void main(String [] args)
	{
		new GameInitializer();
	}
	private MenuBar setupMenuBar()
	{
		MenuBar mb = new MenuBar();
		Menu program = setupProgramMenu();
		
		mb.add(program);
		
		return mb;
	}
	private Menu setupProgramMenu()
	{
		Menu program = new Menu("Program");
		MenuItem skirmish = new MenuItem("Skirmish Game");
		MenuItem aibattle = new MenuItem("AI Competition");
		MenuItem network = new MenuItem("Network Game");
		MenuItem exit = new MenuItem("Exit");
		skirmish.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		aibattle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		network.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		program.add(skirmish);
		program.add(aibattle);
		program.add(network);
		program.addSeparator();
		program.add(exit);
		return program;
	}
}
