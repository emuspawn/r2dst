package starter;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
/**
 * a basic starter for the game, creates a human player and 3 other ai players
 * @author Jack
 *
 */
public class BasicStarter extends JFrame
{
	JFrame f;
	ArrayList<JTextField> tf = new ArrayList<JTextField>();
	
	public BasicStarter()
	{
		super("Basic Starter");
		f = this;
		setSize(300, 400);
		
		add(createSelectorPanel());
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args)
	{
		new BasicStarter();
	}
	private JPanel createPlayerSelector(String playerName, Object[] data)
	{
		JPanel p = new JPanel();
		JLabel name = new JLabel(playerName);
		//JTextField tf = new JTextField(15);
		p.add(name);
		p.add(createList(data));
		//this.tf.add(tf);
		return p;
	}
	private JComponent createList(Object[] data)
	{
		JList list = new JList(data); //data has type Object[]
		//list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //list.setVisibleRowCount(1);
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new  JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.setPreferredSize(new Dimension(100, 60));
		return listScroller;
	}
	private Object[] getCompAINames()
	{
		Object[] o = {"comp tester", "test ai 2"};
		return o;
	}
	private Object[] getHumanAINames()
	{
		Object[] o = {"humanAI tester"};
		return o;
	}
	private JPanel createSelectorPanel()
	{
		JPanel p = new JPanel();
		p.add(createPlayerSelector("human:", getHumanAINames()));
		p.add(createPlayerSelector("player 1:", getCompAINames()));
		p.add(createPlayerSelector("player 2:", getCompAINames()));
		p.add(createPlayerSelector("player 3:", getCompAINames()));
		return p;
	}
}
