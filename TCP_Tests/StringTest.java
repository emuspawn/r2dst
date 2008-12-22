
public class StringTest {
	public StringTest(testRecord tstRec, int testIndex, TCP_Server serv, TCP_Client cli)
	{
		boolean passed;
		
		passed = false;
		System.out.println("Running server string write test");
		if (serv.writeString(0, " test 1 "))
			if (serv.write(0))
			{
				while (!cli.read());
				
				if (cli.readString().equals(" test 1 ")) {
					System.out.println("Test passed: Server string write test");
					passed = true;
				} else
					System.out.println("Test failed: Received string doesn't match");
			}
			else
				System.out.println("Test failed: write failed");
		else
			System.out.println("Test failed: writeString failed");
		
		if (passed)
			tstRec.testPassed(testIndex);
		else
			tstRec.testFailed(testIndex);
		
		passed = false;
		System.out.println("Running client string write test");
		if (cli.writeString("test 2"))
			if (cli.write())
			{
				while (!serv.read(0));
				
				if (serv.readString(0).equals("test 2")) {
					System.out.println("Test passed: client string write test");
					passed = true;
				} else
					System.out.println("Test failed: Received string doesn't match");
			}
			else
				System.out.println("Test failed: write failed");
		else
			System.out.println("Test failed: writeString failed");
		
		if (passed)
			tstRec.testPassed(testIndex);
		else
			tstRec.testFailed(testIndex);
		
		passed = false;
		System.out.println("Running client invalid string test");
		if (cli.writeString("`"))
			System.out.println("Test failed: Allowed string with '`'");
		else
			if (cli.writeString("{OBJ-ST}"))
				System.out.println("Test failed: Allowed string with '{OBJ-ST}'");
			else 
				if (cli.writeString("{OBJ-EN}))"))
					System.out.println("Test failed: Allowed string with '{OBJ-EN}'");
				else {
					System.out.println("Test passed: client invalid string test");
					passed = true;
				}
		
		if (passed)
			tstRec.testPassed(testIndex);
		else
			tstRec.testFailed(testIndex);
		
		passed = false;
		System.out.println("Running server invalid string test");
		if (serv.writeString(0, "`"))
			System.out.println("Test failed: Allowed string with '`'");
		else
			if (serv.writeString(0, "{OBJ-ST}"))
				System.out.println("Test failed: Allowed string with '{OBJ-ST}'");
			else
				if (serv.writeString(0, "{OBJ-EN}"))
					System.out.println("Test failed: Allowed string with '{OBJ-EN}'");
				else {
					System.out.println("Test passed: server invalid string test");
					passed = true;
				}
		
		if (passed)
			tstRec.testPassed(testIndex);
		else
			tstRec.testFailed(testIndex);
	}
}
