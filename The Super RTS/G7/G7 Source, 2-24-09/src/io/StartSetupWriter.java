package io;

import java.io.*;

public final class StartSetupWriter
{
	public static void writeStartSetup(String startSetup, DataOutputStream dos) throws IOException
	{
		dos.writeInt(startSetup.length());
		dos.writeChars(startSetup);
	}
}
