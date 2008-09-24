package driver;

import java.awt.*;
import java.awt.event.*;
import graphics.Camera;

public class WindowDisplay extends Frame
{
	DrawCanvas dc;
	
	public static final long serialVersionUID = 1;
	
	public WindowDisplay(MouseListener ml, KeyListener kl, MouseMotionListener mml, Camera c, Drawer d)
	{
		super("VRPG");
		setSize(300, 300);
		//setLayout(new FlowLayout());
		dc = new DrawCanvas(this, c, d);
		
		add(dc);
		addMouseListener(ml);
		addKeyListener(kl);
		addMouseMotionListener(mml);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		setVisible(true);
	}
	public DrawCanvas getDrawCanvas()
	{
		return dc;
	}
}
class DrawCanvas extends Canvas implements FocusListener
{
	Camera c;
	Drawer d;
	WindowDisplay wd;
	
	public static final long serialVersionUID = 1;
	
	public DrawCanvas(WindowDisplay wd, Camera c, Drawer d)
	{
		this.c = c;
		this.d = d;
		this.wd = wd;
		addFocusListener(this);
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	public void paint(Graphics g)
	{
		Image img = createImage(getWidth(), getHeight());
		c.setWidth(getWidth());
		c.setHeight(getHeight());
		d.drawGame(img.getGraphics());
		g.drawImage(img, 0, 0, null);
	}
	public void focusGained(FocusEvent e)
	{
		wd.requestFocus();
	}
	public void focusLost(FocusEvent e){}
}
