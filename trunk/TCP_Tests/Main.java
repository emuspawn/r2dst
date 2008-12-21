import java.io.IOException;
import java.net.InetAddress;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TCP_Server serv;
		TCP_Client cli;
		
		try {
			serv = new TCP_Server(1030);
			cli = new TCP_Client(InetAddress.getLocalHost(), 1030);
		} catch (IOException e) {
			System.out.println("Test init failed");
			return;
		}
		
		new StringTest(serv, cli);
		new DoubleTest(serv, cli);
		new ObjectTest(serv, cli);
		//new ConnectDisconnectTest(serv, cli);
	}

}
