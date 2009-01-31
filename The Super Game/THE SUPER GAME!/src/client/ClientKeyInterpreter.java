package client;

import java.awt.event.*;
import connection.Connection;
import java.util.ArrayList;

public class ClientKeyInterpreter implements KeyListener, Runnable
{
	Connection c;
	ArrayList<Character> chars = new ArrayList<Character>();
	
	public ClientKeyInterpreter(Connection c)
	{
		this.c = c;
		new Thread(this).start();
	}
	public void run()
	{
		for(;;)
		{
			relayUserActions();
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException e){}
		}
	}
	public void keyPressed(KeyEvent e)
	{
		boolean add = true;
		for(int i = chars.size()-1; i >= 0; i--)
		{
			if(chars.get(i) == e.getKeyChar())
			{
				add = false;
				break;
			}
		}
		if(add)
		{
			chars.add(e.getKeyChar());
		}
	}
	public void keyReleased(KeyEvent e)
	{
		for(int i = chars.size()-1; i >= 0; i--)
		{
			if(chars.get(i) == e.getKeyChar())
			{
				chars.remove(i);
			}
		}
	}
	public void keyTyped(KeyEvent e){}
	public void relayUserActions()
	{
		for(int i = chars.size()-1; i >= 0; i--)
		{
			c.relayUserAction(chars.get(i));
		}
	}
}
