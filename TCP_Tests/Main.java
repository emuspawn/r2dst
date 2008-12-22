import java.io.IOException;
import java.net.InetAddress;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TCP_Server serv;
		TCP_Client cli;
		testRecord tstRec = new testRecord();
		
		try {
			serv = new TCP_Server(1030);
			cli = new TCP_Client(InetAddress.getLocalHost(), 1030);
		} catch (IOException e) {
			System.out.println("Test init failed");
			return;
		}
		
		System.out.println("------------ Running string tests -------------\n");
		new StringTest(tstRec, tstRec.registerTest("String test"), serv, cli);
		System.out.println("\n------------ Running double tests -------------\n");
		new DoubleTest(tstRec, tstRec.registerTest("Double test"), serv, cli);
		System.out.println("\n------------ Running object tests -------------\n");
		new ObjectTest(tstRec, tstRec.registerTest("Object test"), serv, cli);
		System.out.println("\n------- Running Connect/Disconnect tests ------\n");
		new ConnectDisconnectTest(tstRec, tstRec.registerTest("Connect/Disconnect test"));
		
		cli.close();
		serv.close();
		
		System.out.println();
		tstRec.showResults();
	}

}
