import java.util.ArrayList;


public class ObjectTest {
	private TCP_Server serv;
	private TCP_Client cli;
	private int testIndex;
	private testRecord tstRec;
	
	public ObjectTest(testRecord ttRec, int tstIndex, TCP_Server srv, TCP_Client cl)
	{
		serv = srv;
		cli = cl;
		tstRec = ttRec;
		testIndex = tstIndex;
		
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
			tstRec.testFailed(testIndex);
			return;
		}
		
		//Wait for the data to be received
		while (!cli.read());
		
		if (cli.readDouble() != null || cli.readString() != null)
		{
			System.out.println("Test failed!!! Object data corrupt - Report this to Cameron");
			tstRec.testFailed(testIndex);
			return;
		}
			
		ArrayList<String> objStrList = cli.readObjectStringList();
		send2 = new ExampleSendableClass(objStrList);
		    
		if (send2.equals(send1)) {
		   	System.out.println("Test passed: TCP object send and receive test");
		   	tstRec.testPassed(testIndex);
		} else {
		    System.out.println("Test failed: send1 != send2");
		    tstRec.testFailed(testIndex);
		}
	}
	
	public void testSendable(ExampleSendableClass send1)
	{
		ExampleSendableClass send2;
		
		try {
			send2 = new ExampleSendableClass(send1.toSendStringList());
		} catch (Exception e) {
			System.out.println("Failure (Exception)");
			tstRec.testFailed(testIndex);
			return;
		}
		
		if (send1.equals(send2)) {
			System.out.println("Test passed: sendable reconstruction test");
			tstRec.testPassed(testIndex);
		} else {
			System.out.println("Test failed: send1 != send2");
			tstRec.testFailed(testIndex);
		}
	}
}
