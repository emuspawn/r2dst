package classicUI;

import java.awt.*;
import java.awt.event.*;

public class MessageDialog extends Dialog
{
	static final long serialVersionUID = 4;
	
	public MessageDialog(Frame owner, String message, int width, int height)
	{
		super(owner, "Exception", true);
		setupDialog(message, width, height);
	}
	public MessageDialog(Dialog owner, String message, int width, int height)
	{
		super(owner, "Exception", true);
		setupDialog(message, width, height);
	}
	private void setupDialog(String message, int width, int height)
	{
		setSize(width, height);
		setLayout(new FlowLayout());
		add(new Label(message));
		Button close = new Button("Close");
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		add(close);
		setVisible(true);
	}
}
