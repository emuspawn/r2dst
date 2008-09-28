package inputListener;

import java.awt.event.*;

/*
 * meant to be extended by the listener for a program and the method simply overwritten
 * when needed
 */

public class KeyActionListener implements KeyListener
{
	public KeyActionListener(){}
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}
