import java.io.IOException;
import java.net.InetAddress;


public class ConnectDisconnectTest {
	public ConnectDisconnectTest()
	{
		TCP_Server serv1, serv2;
		TCP_Client cli1, cli2;
		
		try {
			serv1 = new TCP_Server(1031);
			serv2 = new TCP_Server(1032);
			cli1 = new TCP_Client(InetAddress.getLocalHost(), 1031);
			cli2 = new TCP_Client(InetAddress.getLocalHost(), 1032);
		} catch (IOException e) {
			System.out.println("Connect/Disconnect test init failed");
			return;
		}
		
		while (serv1.getClientCount() == 0 || serv2.getClientCount() == 0);
		
		System.out.println("Running server then client close test");
		if (serv1.close())
			if (cli1.close())
				System.out.println("Test passed: Server then client close");
			else
				System.out.println("Test failed: Client 1 close failed");
		else
			System.out.println("Test failed: Server 1 close failed");
		
		System.out.println("Running client then server close test");
		if (cli2.close())
			if (serv2.close())
				System.out.println("Test passed: Client then server close");
			else
				System.out.println("Test failed: Server 2 close failed");
		else
			System.out.println("Test failed: Client 2 close failed");
		
		System.out.println("Running read on closed test");
		if (serv1.read(0))
			System.out.println("Test failed (Server): Attempt to read on closed socket succeeded");
		else
			if (cli1.read())
				System.out.println("Test failed (Client): Attempt to read on closed socket succeeded");
			else
				System.out.println("Test passed: Read on closed");
		
		System.out.println("Running write on closed test");
		cli2.writeString("test");
		cli2.writeDouble(1.1);
		if (cli2.write())
				System.out.println("Test failed (Client): write succeeded");
		else {
				serv2.writeDouble(0, 1.2);
				serv2.writeString(0, "test serv");
				if (serv2.write(0))
					System.out.println("Test failed (Server): write succeeded");
				else
					System.out.println("Test passed: write on closed");
		}
	}
}
