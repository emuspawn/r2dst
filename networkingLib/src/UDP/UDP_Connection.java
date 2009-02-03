package UDP;
import java.io.IOException;
import java.net.*;


public class UDP_Connection extends Thread {
	private DatagramSocket dsock;
	
	public UDP_Connection(int prt) throws SocketException
	{
		dsock = new DatagramSocket(prt);
	}
	
	public DatagramPacket receiveDatagram(int packetSize)
	{
		DatagramPacket pack = new DatagramPacket(new byte[packetSize], packetSize);
		try {
			dsock.receive(pack);
		} catch (IOException e) {
			return null;
		}
		return pack;
	}
	
	public boolean sendDatagram(DatagramPacket pack)
	{
		try {
			dsock.send(pack);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean close()
	{
		dsock.close();
		return true;
	}
}
