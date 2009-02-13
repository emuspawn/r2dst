package io;

import world.World;
import java.util.ArrayList;
import world.owner.Owner;
import java.io.*;
import factory.*;

/*
 * reads the start setup and carries out the instructions (adding units,
 * creating buildings, setting starting resources, etc)
 */

public class StartSetupReader
{
	public static String readStartSetup(DataInputStream dis) throws IOException
	{
		String s = new String();
		int num = dis.readInt();
		System.out.println("num = "+num);
		for(int i = 0; i < num; i++)
		{
			s += dis.readChar();
		}
		return s;
	}
	public static void readStartSetup(World w, DataInputStream dis) throws IOException
	{
		StartSetupReader ssr = new StartSetupReader();
		String temp = new String();
		char c;
		int length = dis.readInt();
		for(int i = 0; i < length; i++)
		{
			c = dis.readChar();
			if(c != '\n')
			{
				temp+=c;
			}
		}
		String[] s = temp.split(";");
		String version = s[0];
		String description = s[1];
		ssr.readUnits(w, s);
	}
	private void readUnits(World w, String[] s)
	{
		ArrayList<Owner> o = w.getOwners();
		int uindex = -1; //unit index
		for(int i = 0; i < s.length; i++)
		{
			if(s[i].equalsIgnoreCase("units"))
			{
				uindex = i;
				break;
			}
		}
		String[] temp = s[uindex+1].split(" ");
		String type;
		int number;
		int player;
		int count = 1;
		while(temp.length != 1)
		{
			type = temp[0];
			number = Integer.parseInt(temp[1]);
			player = Integer.parseInt(temp[2]);
			for(int a = number-1; a >= 0; a--)
			{
				if(player != 0)
				{
					w.registerElement(UnitFactory.makeUnit(o.get(player-1), type, o.get(player-1).getStartingLocation()));
				}
				else
				{
					for(int q = o.size()-1; q >= 0; q--)
					{
						w.registerElement(UnitFactory.makeUnit(o.get(q), type, o.get(q).getStartingLocation()));
					}
				}
			}
			count++;
			temp = s[uindex+count].split(" ");
		}
	}
}
