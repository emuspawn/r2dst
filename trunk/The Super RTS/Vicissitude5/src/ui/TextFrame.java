package ui;

import javax.swing.*;

/**
 * a jframe that merely displays text, disposes itself defaultly
 * when clicked
 * @author Jack
 *
 */
public class TextFrame extends JFrame
{
	/**
	 * creates a visible text frame that displays text
	 * @param text the text to be displayed
	 */
	public TextFrame(String text)
	{
		JTextArea ta = new JTextArea();
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setText(text);
		JScrollPane sp = new JScrollPane(ta);
		setSize(250, 400);
		add(sp);
		setVisible(true);
	}
}
