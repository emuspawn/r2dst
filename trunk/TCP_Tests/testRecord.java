import java.util.ArrayList;


public class testRecord {
	private ArrayList<String> tests;
	private ArrayList<Integer> failures;
	private ArrayList<Integer> passes;
	
	public testRecord()
	{
		tests = new ArrayList<String>();
		failures = new ArrayList<Integer>();
		passes = new ArrayList<Integer>();
	}
	
	public int registerTest(String testName)
	{
		tests.add(testName);
		failures.add(0);
		passes.add(0);
		return tests.indexOf(testName);
	}
	
	public void testFailed(int testIndex)
	{
		increment(failures, testIndex);
	}
	
	public void testPassed(int testIndex)
	{
		increment(passes, testIndex);
	}
	
	public void showResults()
	{
		int totalPassed = 0, totalFailed = 0, totalRun = 0;
		int passed, failed, total;
		for (int i = 0; i < tests.size(); i++)
		{
			passed = passes.get(i);
			failed = failures.get(i);
			total = passed + failed;
			
			totalPassed += passed;
			totalFailed += failed;
			totalRun += total;
			
			System.out.println(tests.get(i)+"\nPassed: "+passed+"\nFailed: "+failed+"\nTotal: "+total+"\n");
		}
		
		System.out.println("Total passed: "+totalPassed+"\nTotal failed: "+totalFailed+"\nTotal run: "+totalRun);
	}
	
	private void increment(ArrayList<Integer> ints, int index)
	{
		int oldNum = ints.remove(index);
		
		oldNum++;
		
		ints.add(index, oldNum);
	}
}
