package ui;

import java.awt.*;
import javax.swing.*;

/**
 * a full screen or normal window frame, uses the graphics
 * device to check if full screen is supported, if it is
 * full screen mode is utilized
 * @author Secondary
 *
 */
public class GameFrame extends JFrame
{
	private static final long serialVersionUID = -8378081618633035054L;
	boolean isFullScreen = false;
	
	/**
	 * creates a game frame that is either windowed or full screen, if the
	 * graphics configuration on the machine does not allow full screen mode
	 * then the frame will resort to windowed mode regardless of the passed
	 * window parameter
	 * @param device the graphics device to be used by the frame
	 * @param windowed true if the frame should be windowed, full screen otherwise
	 */
	public GameFrame(GraphicsDevice device, boolean windowed)
	{
		super(device.getDefaultConfiguration());
		isFullScreen = device.isFullScreenSupported();
		
		if(isFullScreen)
		{
			isFullScreen = !windowed;
		}
		setUndecorated(isFullScreen);
		setResizable(!isFullScreen);
		
		
		if(isFullScreen)
		{
            device.setFullScreenWindow(this);
            validate();
        }
		else
		{
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setVisible(true);
		}
	}
	/**
	 * gets the default graphics device as determined by the local
	 * graphics environment
	 * @return returns the default graphics device
	 */
	public static GraphicsDevice getDegaultGraphicsDevice()
	{
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		return device;
	}
}
