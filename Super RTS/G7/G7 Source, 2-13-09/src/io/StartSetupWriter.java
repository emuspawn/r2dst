package io;

import java.io.*;

public class StartSetupWriter
{
	public static void writeStartSetup(String startSetup, DataOutputStream dos) throws IOException
	{
		dos.writeInt(startSetup.length());
		dos.writeChars(startSetup);
	}
}
