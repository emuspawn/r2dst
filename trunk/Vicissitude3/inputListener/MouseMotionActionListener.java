package inputListener;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/*
 * meant to be extended by the listener for a program and the method simply overwritten
 * when needed
 */

public class MouseMotionActionListener implements MouseMotionListener
{
	public MouseMotionActionListener(){}
	public void mouseMoved(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
}
