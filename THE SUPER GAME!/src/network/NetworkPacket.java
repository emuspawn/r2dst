package network;

import java.io.Serializable;

public class NetworkPacket implements Serializable {
	private static final long serialVersionUID = 7320866075242221084L;
	public int type;
	public Object[] data;
}
