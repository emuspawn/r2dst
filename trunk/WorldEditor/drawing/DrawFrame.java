package drawing;

import java.awt.*;
import driver.MainThread;
import tileSystem.TileSystem;
import gui.WorldEditorMenuBar;
import java.awt.event.*;

public class DrawFrame extends Frame
{
	public static final long serialVersionUID = 1;
	
	DrawCanvas dc;
	
	public DrawFrame(Drawer d, MainThread mt, TileSystem ts)
	{
		super("World Editor");
		setSize(500, 500);
		dc = new DrawCanvas(this, d);
		setLayout(new FlowLayout());
		add(dc);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		setMenuBar(new WorldEditorMenuBar(this, mt, ts));
		//setVisible(true);
	}
	public void repaintCanvas()
	{
		dc.repaint();
	}
	public Canvas getDrawCanvas()
	{
		return dc;
	}
}
class DrawCanvas extends Canvas implements FocusListener
{
	public static final long serialVersionUID = 2;
	
	DrawFrame df;
	Drawer d;
	
	public DrawCanvas(DrawFrame df, Drawer d)
	{
		this.df = df;
		this.d = d;
		addFocusListener(this);
		setSize(df.getWidth(), df.getHeight());
		setBackground(Color.green);
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	public void paint(Graphics g)
	{
		//System.out.println("painting");
		Image img = createImage(getWidth(), getHeight());
		d.performDrawFunctions(img.getGraphics());
		g.drawImage(img, 0, 0, null);
		//System.out.println("paint finished");
	}
	public void focusGained(FocusEvent e)
	{
		//df.requestFocus();
	}
	public void focusLost(FocusEvent e){}
}
