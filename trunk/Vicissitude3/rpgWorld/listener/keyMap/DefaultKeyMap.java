package rpgWorld.listener.keyMap;

import rpgWorld.listener.KeyAction;

public class DefaultKeyMap implements KeyMap
{
	public DefaultKeyMap(){}
	public int getAction(char c)
	{
		if(c == 'w')
		{
			return KeyAction.MOVE_HERO_FORWARD;
		}
		else if(c == 'd')
		{
			return KeyAction.ROTATE_HERO_POSITIVE;
		}
		else if(c == 'a')
		{
			return KeyAction.ROTATE_HERO_NEGATIVE;
		}
		return -1;
	}
}
