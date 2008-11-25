package graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import inputListener.*;

//finds the graphics related things need for full screen exclusive mode

public class GraphicsFinder
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
	
	
	
	public GraphicsFinder(MouseActionListener mal, KeyActionListener kal, MouseMotionActionListener mmal)
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
		mainFrame.addMouseListener(mal);
		mainFrame.addKeyListener(kal);
		mainFrame.addMouseMotionListener(mmal);
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
}