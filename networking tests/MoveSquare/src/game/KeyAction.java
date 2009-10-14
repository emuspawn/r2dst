package game;

import java.io.*;

public class KeyAction
{
	byte owner;
	char c; //the character pressed
	boolean pressed;
	
	public KeyAction(byte owner, char c, boolean pressed)
	{
		this.owner = owner;
		this.c = c;
		this.pressed = pressed;
	}
	public byte getOwner()
	{
		return owner;
	}
	public char getCharacter()
	{
		return c;
	}
	public boolean isPressed()
	{
		return pressed;
	}
	public byte[] toByteArray() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeByte(owner);
		dos.writeChar(c);
		dos.writeBoolean(pressed);
		return baos.toByteArray();
	}
	public String toString()
	{
		return "owner="+owner+", char="+c+", pressed="+pressed;
	}
}
