package connection;

import java.util.ArrayList;
import world.Element;

public abstract class Connection
{
	protected int index;
	public abstract void relayUserAction(char c);
	public abstract ArrayList<Element> getVisibleElements();
	public abstract void sendScreenDimensions(int width, int height);
	public abstract String getUserName();
	public abstract void setIndex(int setter);
}
