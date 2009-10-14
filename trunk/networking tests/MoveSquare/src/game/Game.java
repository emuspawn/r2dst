package game;

import java.io.*;
import java.util.HashSet;

import ui.UIFrame;
import ui.userIO.UserInputInterpreter;

public class Game extends UserInputInterpreter
{
	GameClient gc;
	Unit[] u = new Unit[2];
	
	long time = 0;
	long totalTime = 0;
	long iterations = 0;
	
	HashSet<Character> kdown = new HashSet<Character>(); //keys down
	
	public Game()
	{
		gc = new GameClient(this, "QAYPN", 4444);
		if(gc.isConnected())
		{
			for(int i = 0; i < u.length; i++)
			{
				u[i] = new Unit(50, 50, 50, 50);
			}
			Display d = new Display(this);
			UIFrame uif = new UIFrame(this, d);
		}
	}
	public Unit[] getUnits()
	{
		return u;
	}
	public void keyAction(char c, boolean pressed)
	{
		//if((pressed && !kdown.contains(c)) || !pressed)
		//{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream bdos = new DataOutputStream(baos);
			try
			{
				bdos.writeByte(gc.getID());
				bdos.writeChar(c);
				bdos.writeBoolean(pressed);
			}
			catch(IOException e){}
			gc.write(baos.toByteArray());
			
			if(pressed)
			{
				kdown.add(c);
			}
			else
			{
				kdown.remove(c);
			}
		//}
		//System.out.println("command written: "+new KeyAction(gc.getID(), c, pressed));
	}
	public void mouseAction(int x, int y, boolean pressed, boolean rightClick)
	{
		
	}
	public void mouseMoveAction(int x, int y, boolean dragged, boolean rightClick)
	{
		
	}
	public void updateGame(KeyAction[] ka, double tdiff)
	{
		iterations++;
		if(time != 0)
		{
			totalTime += System.currentTimeMillis()-time;
		}
		else
		{
			time = System.currentTimeMillis();
			iterations = 0;
		}
		boolean[] b = new boolean[u.length];
		for(int i = 0; i < b.length; i++)
		{
			b[i] = false;
		}
		for(int i = 0; i < ka.length; i++)
		{
			if(ka[i].isPressed())
			{
				if(ka[i].getCharacter() == 'w')
				{
					u[ka[i].getOwner()].moveUnit(0, 1, tdiff);
					//u[ka[i].getOwner()].setMoving(true);
					b[ka[i].getOwner()] = true;
				}
				else if(ka[i].getCharacter() == 'd')
				{
					u[ka[i].getOwner()].moveUnit(1, 0, tdiff);
					//u[ka[i].getOwner()].setMoving(true);
					b[ka[i].getOwner()] = true;
				}
				else if(ka[i].getCharacter() == 's')
				{
					u[ka[i].getOwner()].moveUnit(0, -1, tdiff);
					//u[ka[i].getOwner()].setMoving(true);
					b[ka[i].getOwner()] = true;
				}
				else if(ka[i].getCharacter() == 'a')
				{
					u[ka[i].getOwner()].moveUnit(-1, 0, tdiff);
					//u[ka[i].getOwner()].setMoving(true);
					b[ka[i].getOwner()] = true;
				}
			}
		}
		for(int i = 0; i < u.length; i++)
		{
			if(b[i])
			{
				u[i].updateUnit(tdiff);
			}
		}
		if(iterations % 1000 == 0 && iterations != 0)
		{
			System.out.println("iterations per second = "+iterations+" / "+totalTime+" = "+(iterations/(totalTime/1000.0)));
		}
		//u[0].moveUnit(1, 1, tdiff);
		//System.out.println("updating, tdiff = "+tdiff);
	}
	public static void main(String[] args)
	{
		new Game();
	}
}
