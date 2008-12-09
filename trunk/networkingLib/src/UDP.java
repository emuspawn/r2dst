import java.io.IOException;
import java.net.*;

public class UDP {
	private DatagramSocket sock;
	
	public UDP(int port) throws SocketException
	{
		sock = new DatagramSocket(port);
	}
	
	//Receives a UDP datagram (blocks)
	public boolean receiveDatagram(byte[] buffer)
	{
		DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
		try {
			sock.receive(datagram);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	//Sends a UDP datagram
	public boolean sendDatagram(InetAddress addr, int port, byte[] buffer)
	{
		DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, addr, port);

		try {
			sock.send(datagram);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
