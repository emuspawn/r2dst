package connection;

/*
 * c is the character on the keyboard that, when pressed, initations the action defined by command,
 * b is the byte sent to the world denoting the action
 */

public class UserAction
{
	char c;
	String ua; //user action
	byte b;
	
	public UserAction(char c, String ua, byte b)
	{
		this.c = c;
		this.ua = ua;
		this.b = b;
	}
	public String toString()
	{
		return new String(c+" - "+ua+" - "+b);
	}
	public byte getByte()
	{
		return b;
	}
	public String getUserAction()
	{
		return ua;
	}
	public char getCharacter()
	{
		return c;
	}
}
