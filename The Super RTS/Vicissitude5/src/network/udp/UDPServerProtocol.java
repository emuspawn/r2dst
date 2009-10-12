package network.udp;

import java.net.*;

/**
 * used by the udp server to dertermine the buffer size and how to deal with
 * incomming packets, interpretPacket method called every iteration of the
 * server thread
 * @author Jack
 *
 */

public abstract class UDPServerProtocol
{
	private int bufferSize;
	
	public UDPServerProtocol(int bufferSize)
	{
		this.bufferSize = bufferSize;
	}
	/**
	 * gets the buffer size for the packet
	 * @return returns the buffer size
	 */
	public int getBufferSize()
	{
		return bufferSize;
	}
	/**
	 * interprets the incomming packet
	 * @param packet the packet received by the server
	 * @param buf the buffer of bytes received by the server
	 */
	public abstract void interpretPacket(DatagramPacket packet, byte[] buf);
}
