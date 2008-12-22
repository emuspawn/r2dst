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
		
		System.out.println("------------ Running string tests -------------\n");
		new StringTest(serv, cli);
		System.out.println("\n------------ Running double tests -------------\n");
		new DoubleTest(serv, cli);
		System.out.println("\n------------ Running object tests -------------\n");
		new ObjectTest(serv, cli);
		System.out.println("\n------- Running Connect/Disconnect tests ------\n");
		new ConnectDisconnectTest();
		
		cli.close();
		serv.close();
	}

}
