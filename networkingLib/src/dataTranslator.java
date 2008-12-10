import java.util.ArrayList;
import java.util.Scanner;


public class dataTranslator {
	public static ArrayList<Integer> stringToInt(String str)
	{
		ArrayList<Integer> intList = new ArrayList<Integer>();
		Scanner scan = new Scanner(str).useDelimiter(",");
		
		while (scan.hasNextInt())
		{
			intList.add(scan.nextInt());
		}
		
		return intList;
	}
	
	public static String intToString(ArrayList<Integer> ints)
	{
		String ret = "";
		for (Integer i : ints)
		{
			ret += i.toString()+",";
		}
		
		return ret;
	}
}
