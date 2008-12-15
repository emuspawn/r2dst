package drawing;

import javax.swing.JFrame;

public class DrawFrame extends JFrame
{
	public DrawCanvas dc;

	public DrawFrame()
	{
		super("super");
		dc = new DrawCanvas(this);
		setSize(500, 500);
		add(dc);
		setVisible(true);
	}
}
