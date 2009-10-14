package network;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 * a simple class for starting the server
 * @author Jack
 *
 */
public class ServerStarter extends JFrame
{
	JTextField port = new JTextField(15);
	JFrame owner;
	
	public ServerStarter()
	{
		super("Server Starter");
		setSize(250, 100);
		setLocationRelativeTo(null);
		owner = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(new JLabel("Port:"));
		p.add(port);
		JButton start = new JButton("Start");
		add(p);
		add(start);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int portNum = Integer.parseInt(port.getText());
					new ForwardingServer(portNum);
					new ExitDialog(owner, portNum);
					setVisible(false);
				}
				catch(NumberFormatException a)
				{
					System.out.println("port entered incorrectly");
				}
			}
		});
		setVisible(true);
	}
	public static void main(String[] args)
	{
		new ServerStarter();
	}
}
class ExitDialog extends JDialog
{
	public ExitDialog(JFrame owner, int port)
	{
		super(owner, "", true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setSize(200, 80);
		setLayout(new FlowLayout());
		setLocationRelativeTo(owner);
		add(new JLabel("Server is running, port = "+port));
		JButton exit = new JButton("Exit");
		add(exit);
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		setVisible(true);
	}
}
