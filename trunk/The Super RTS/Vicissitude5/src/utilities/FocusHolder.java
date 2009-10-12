package utilities;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.awt.Component;

/**
 * makes sure a given component maintains focus
 * @author Jack
 *
 */
public class FocusHolder implements FocusListener
{
	private Component c;
	
	public FocusHolder(Component c)
	{
		this.c = c;
	}
	public void focusGained(FocusEvent e){}
	public void focusLost(FocusEvent e)
	{
		c.requestFocus();
	}
}
