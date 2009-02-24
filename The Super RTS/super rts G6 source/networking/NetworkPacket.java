package networking;

import java.io.Serializable;

public class NetworkPacket implements Serializable {
	private static final long serialVersionUID = -1724508946658096495L;
	int type;
	Object[] data;
}
