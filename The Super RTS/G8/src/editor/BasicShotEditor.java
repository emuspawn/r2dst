package editor;

import io.ShotWriter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.*;

import sgEngine.EngineConstants;
import world.shot.Shot;

/**
 * a basic unit editor
 * @author Jack
 *
 */
public class BasicShotEditor extends JFrame
{
	JFrame f;
	JTextField name;
	JTextField damage;
	JTextField movement;
	JTextField width;
	JTextField height;
	JTextField depth;
	
	public BasicShotEditor()
	{
		super("Basic Shot Editor");
		f = this;
		setSize(200, 280);
		
		add(createGeneralPanel());
		setJMenuBar(createMenuBar());
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args)
	{
		new BasicShotEditor();
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
					System.out.println("saving shot...");
					String dir = System.getProperty("user.dir")+System.getProperty("file.separator")+
							"shots"+System.getProperty("file.separator");
					System.out.println("dir = "+dir);
					File f = new File(dir+name.getText()+".shot");
					System.out.println("file name = "+f.getName());
					FileOutputStream fos = new FileOutputStream(f);
					DataOutputStream dos = new DataOutputStream(fos);
					ShotWriter.writeShot(dos, name.getText(), Double.parseDouble(damage.getText()), 
							Double.parseDouble(movement.getText()), Double.parseDouble(width.getText()), 
							Double.parseDouble(height.getText()), Double.parseDouble(depth.getText()));
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
					Shot s = EngineConstants.shotFactory.makeShot(name.getText());
					damage.setText(""+s.getDamage());
					movement.setText(""+s.getMovement());
					width.setText(""+s.getWidth());
					height.setText(""+s.getHeight());
					depth.setText(""+s.getDepth());
				}
				catch(NullPointerException a)
				{
					System.out.println("incorrect name, shot load failed");
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
	private JPanel createGeneralPanel()
	{
		JPanel p = createPanel();
		name = new JTextField(15);
		damage = new JTextField(15);
		movement = new JTextField(15);
		width = new JTextField(15);
		height = new JTextField(15);
		depth = new JTextField(15);
		p.add(new JLabel("Name:"));
		p.add(name);
		p.add(new JLabel("Damage:"));
		p.add(damage);
		p.add(new JLabel("Movement:"));
		p.add(movement);
		p.add(new JLabel("Width:"));
		p.add(width);
		p.add(new JLabel("Height:"));
		p.add(height);
		p.add(new JLabel("Depth:"));
		p.add(depth);
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
