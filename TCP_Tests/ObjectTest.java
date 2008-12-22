import java.util.ArrayList;


public class ObjectTest {
	private TCP_Server serv;
	private TCP_Client cli;
	
	public ObjectTest(TCP_Server srv, TCP_Client cl)
	{
		serv = srv;
		cli = cl;
		
		ExampleSendableClass send1 = new ExampleSendableClass(1, 5.6, "test string");
		
		System.out.println("Running sendable reconstruction test");
		testSendable(send1);
		
		System.out.println("Running TCP object send and receive test");
		testTCPsendable(send1);
	}
	
	public void testTCPsendable(ExampleSendableClass send1)
	{
		ExampleSendableClass send2;
		
		//Wait for client connection
		while (serv.getClientCount() == 0);
		
		serv.writeObject(0, send1);
		if (!serv.write(0))
		{
			System.out.println("no data to send");
			return;
		}
		
		//Wait for the data to be received
		while (!cli.read());
		
		if (cli.readDouble() != null || cli.readString() != null)
		{
			System.out.println("Test failed!!! Object data corrupt - Report this to Cameron");
			return;
		}
			
		ArrayList<String> objStrList = cli.readObjectStringList();
		send2 = new ExampleSendableClass(objStrList);
		    
		if (send2.equals(send1))
		   	System.out.println("Test passed: TCP object send and receive test");
		else
		    System.out.println("Test failed: send1 != send2");
	}
	
	public void testSendable(ExampleSendableClass send1)
	{
		ExampleSendableClass send2;
		
		try {
			send2 = new ExampleSendableClass(send1.toSendStringList());
		} catch (Exception e) {
			System.out.println("Failure (Exception)");
			return;
		}
		
		if (send1.equals(send2))
			System.out.println("Test passed: sendable reconstruction test");
		else
			System.out.println("Test failed: send1 != send2");
	}
}
