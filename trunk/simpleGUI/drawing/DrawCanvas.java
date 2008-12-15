package drawing;
import java.awt.*;
import java.awt.event.*;

public class DrawCanvas extends Canvas implements Runnable, FocusListener
{
	public DrawFrame df;
	
	public DrawCanvas(DrawFrame df)
	{
		setSize(500,500);
		this.df = df;
		addFocusListener(this);
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
		//put stuff to draw here
		
	}
	public void focusGained(FocusEvent e)
	{
		df.requestFocus();
	}
	
	public void focusLost(FocusEvent e)
	{
		
	}
}