package io;

import java.io.*;
import connection.Account;

public class AccountReader
{
	public static Account readAccount(DataInputStream dis) throws IOException
	{
		int version = dis.readInt();
		
		String name = new String();
		for(int i = dis.readInt()-1; i >= 0; i--)
		{
			name+=dis.readChar();
		}
		int level = dis.readInt();
		int gold = dis.readInt();
		
		return new Account(name, level, gold);
	}
}
