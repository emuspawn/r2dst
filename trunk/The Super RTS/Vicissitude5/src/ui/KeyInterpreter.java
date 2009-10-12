package ui;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import ui.controlScheme.*;

/**
 * the generic type "T" must be of the type that needs to be processed by the Keys,
 * works in conjunction with ControlScheme
 * @author Secondary
 * @param <T> the type that needs to be processed
 */

public class KeyInterpreter <T> implements KeyListener
{
	ArrayList<Controllable<T>> c = new ArrayList<Controllable<T>>();
	
	public KeyInterpreter(ArrayList<Controllable<T>> c)
	{
		this.c = c;
	}
	public void keyPressed(KeyEvent e)
	{
		for(int i = c.size()-1; i >= 0; i--)
		{
			c.get(i).getControlScheme().processKeyPress(e.getKeyChar());
		}
	}
	public void keyReleased(KeyEvent e)
	{
		for(int i = c.size()-1; i >= 0; i--)
		{
			c.get(i).getControlScheme().processKeyRelease(e.getKeyChar());
		}
	}
	public void keyTyped(KeyEvent e){}
}
