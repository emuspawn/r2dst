package network.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * interpretStream method run once per iteration of the tcp server thread
 * @author Jack
 *
 */
public abstract class TCPProtocol
{
	/**
	 * interprets the data stream from the socket
	 * @param dis input stream
	 * @param dos output stream
	 * @return returns true if the thread should be terminated, false otherwise
	 */
	public abstract boolean interpretStream(DataInputStream dis, DataOutputStream dos);
}
