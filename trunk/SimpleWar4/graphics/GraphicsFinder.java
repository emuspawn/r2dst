package graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.event.*;

import ui.*;
import world.World;

//finds the graphics related things need for full screen exclusive mode

public class GraphicsFinder implements MouseListener, KeyListener, MouseMotionListener
{
	Frame mainFrame;
	GraphicsEnvironment env;
	GraphicsDevice device;
	GraphicsConfiguration gc;
	Graphics g;
	BufferStrategy bufferStrategy;
	int numBuff;
	private static DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[] {
        new DisplayMode(800, 600, 32, 0),
        new DisplayMode(800, 600, 16, 0),
        new DisplayMode(800, 600, 8, 0),
        new DisplayMode(1024, 768, 32, 0),
        new DisplayMode(1024, 768, 16, 0),
        new DisplayMode(1024, 768, 8, 0),
        new DisplayMode(1280, 768, 32, 0),
        new DisplayMode(1280, 768, 16, 0),
        new DisplayMode(1280, 768, 8, 0),
        new DisplayMode(1280, 800, 32, 0),
        new DisplayMode(1280, 800, 16, 0),
        new DisplayMode(1280, 800, 8, 0)
    };
	
	boolean rightClick = false;
	boolean leftClick = false;
	
	
	
	
	KeyActionDeterminer kad;
	MouseClickActionDeterminer mcad;
	public Point mouseLocation = new Point(0, 0);
	
	
	
	public GraphicsFinder(World w, Camera c)
	{
		numBuff = 3;
		env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = env.getDefaultScreenDevice();
		gc = device.getDefaultConfiguration();
		if (device.isDisplayChangeSupported()) {
            chooseBestDisplayMode(device);
        }
		mainFrame = new Frame(gc);
		mainFrame.setUndecorated(true);
		mainFrame.setIgnoreRepaint(true);
		device.setFullScreenWindow(mainFrame);
		mainFrame.createBufferStrategy(numBuff);
		bufferStrategy = mainFrame.getBufferStrategy();
		g = bufferStrategy.getDrawGraphics();
		//adds listeners
		mainFrame.addMouseListener(this);
		mainFrame.addKeyListener(this);
		mainFrame.addMouseMotionListener(this);
		
		
		kad = new KeyActionDeterminer(w, c);
		mcad = new MouseClickActionDeterminer(w, c);
	}
	private static DisplayMode getBestDisplayMode(GraphicsDevice device) {
        for (int x = 0; x < BEST_DISPLAY_MODES.length; x++) {
            DisplayMode[] modes = device.getDisplayModes();
            for (int i = 0; i < modes.length; i++) {
                if (modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth()
                   && modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight()
                   && modes[i].getBitDepth() == BEST_DISPLAY_MODES[x].getBitDepth()
                   ) {
                    return BEST_DISPLAY_MODES[x];
                }
            }
        }
        return null;
    }
	public static void chooseBestDisplayMode(GraphicsDevice device) {
        DisplayMode best = getBestDisplayMode(device);
        if (best != null) {
            device.setDisplayMode(best);
        }
    }
	public BufferStrategy getBufferStrategy()
	{
		return bufferStrategy;
	}
	public void changeNumBuffers(int changeAmount)
	{
		numBuff = changeAmount;
		mainFrame.createBufferStrategy(numBuff);
	}
	public Graphics getGraphics()
	{
		g = bufferStrategy.getDrawGraphics();
		return g;
	}
	public int getHeight()
	{
		return mainFrame.getBounds().height;
	}
	public int getWidth()
	{
		return mainFrame.getBounds().width;
	}
	public Frame getMainFrame()
	{
		return mainFrame;
	}
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == e.BUTTON1)
		{
			leftClick = true;
		}
		if(e.getButton() == e.BUTTON3)
		{
			rightClick = true;
		}
	}
	public void mouseReleased(MouseEvent e)
	{
		rightClick = false;
		leftClick = false;
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e)
	{
		mcad.performMouseActions(e, e.getButton());
	}
	public void keyPressed(KeyEvent e)
	{
		
	}
	public void keyReleased(KeyEvent e)
	{
		
	}
	public void keyTyped(KeyEvent e)
	{
		kad.performKeyActions(e);
	}
	public void mouseMoved(MouseEvent e)
	{
		mouseLocation = e.getPoint();
	}
	public void mouseDragged(MouseEvent e)
	{
	}
}
