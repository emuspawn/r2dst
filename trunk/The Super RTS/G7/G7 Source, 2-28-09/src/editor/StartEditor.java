package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import io.*;
import java.io.*;

/*
 * edits the files that control how the game starts (once
 * game play actually begins, not start screen)
 */

public class StartEditor extends JFrame
{
	private static final long serialVersionUID = 1L;
	JTextArea ta;
	
	public StartEditor()
	{
		super("Start Setup Editor V1");
		setSize(300, 500);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new SaveDialog(ta.getText());
			}
		});
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new LoadDialog(ta);
			}
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		file.add(save);
		file.add(load);
		file.addSeparator();
		file.add(exit);
		JMenu help = new JMenu("Help");
		JMenuItem formHelp = new JMenuItem("Form Help");
		formHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new FormHelpDialog();
			}
		});
		help.add(formHelp);
		mb.add(file);
		mb.add(help);
		
		ta = new JTextArea();
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		JScrollPane sp = new JScrollPane(ta);
		
		add(sp);
		setJMenuBar(mb);
		setVisible(true);
	}
	public static void main(String[] args)
	{
		new StartEditor();
	}
}
class LoadDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	JTextField tf;
	JTextArea ss; //start setup
	
	public LoadDialog(JTextArea startSetup)
	{
		ss = startSetup;
		setSize(300, 130);
		tf = new JTextField(15);
		setLayout(new FlowLayout());
		add(new JLabel("File Name:"));
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		JButton save = new JButton("Load");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+tf.getText()+".ss");
					FileInputStream fis = new FileInputStream(f);
					DataInputStream dis = new DataInputStream(fis);
					String temp = StartSetupReader.readStartSetup(dis);
					ss.setText(temp);
					dis.close();
					dispose();
				}
				catch(IOException a)
				{
					a.printStackTrace();
				}
			}
		});
		add(tf);
		add(save);
		add(cancel);
		add(new JLabel("Do not add the file extension."));
		setVisible(true);
	}
}
class SaveDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	JTextField tf;
	String ss; //start setup
	
	public SaveDialog(String startSetup)
	{
		ss = startSetup;
		setSize(300, 130);
		tf = new JTextField(15);
		setLayout(new FlowLayout());
		add(new JLabel("File Name:"));
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+tf.getText()+".ss");
					FileOutputStream fos = new FileOutputStream(f);
					DataOutputStream dos = new DataOutputStream(fos);
					StartSetupWriter.writeStartSetup(ss, dos);
					dispose();
				}
				catch(IOException a)
				{
					a.printStackTrace();
				}
			}
		});
		add(tf);
		add(save);
		add(cancel);
		add(new JLabel("Do not add the file extension."));
		setVisible(true);
	}
}
class FormHelpDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	public FormHelpDialog()
	{
		setSize(300, 500);
		//setLayout(new FlowLayout());
		String s = new String();
		//intro
		s+="The following is an example of how to set up the file ";
		s+="so that it save correctly. The example starts at \"units\" ";
		s+="and goes to \"end\". Every argument is delineated by spaces ";
		s+="and every line end is marked by a ';'. To add notes for any ";
		s+="reason, type \"//\" at the start of the line (still type ';'";
		s+="at the end of the line. A '0' in the player spot means that the";
		s+="element will be given to everyone. Case does not matter.";
		
		s+="\n\n";
		//notes
		s+="notes:\n";
		
		s+="1. In \"units\", first is unit type/name, second is how ";
		s+="many, and third is the player that is to receive the units.\n";
		
		s+="2. Buildings count as units. To add them to the starting units ";
		s+="follow the form of adding units.\n";
		
		s+="3. In \"resources\", first is the type of resource, second is the amount, ";
		s+="third is the player.\n";
		
		s+="\n\n";
		
		s+="--version--;\n";
		s+="--description--;\n";
		s+="UNITS;\n";
		s+="miscUnitName 7 0;\n";
		s+="anotherUnit 3 1;\n";
		s+="unit6 27 3;\n";
		s+="RESOURCES;\n";
		s+="tree 10 0;\n";
		s+="metal 3;\n";
		s+="ore 72 2;\n";
		s+="END;\n";
		
		JTextArea ta = new JTextArea();
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setText(s);
		ta.setSize(300, 300);
		JScrollPane sp = new JScrollPane(ta);
		JButton done = new JButton("Done");
		done.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		add(sp);
		setVisible(true);
	}
}