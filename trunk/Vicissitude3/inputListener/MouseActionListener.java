package inputListener;

import java.awt.event.*;

/*
 * meant to be extended by the listener for a program and the method simply overwritten
 * when needed
 */

public class MouseActionListener implements MouseListener
{
	public MouseActionListener(){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}
