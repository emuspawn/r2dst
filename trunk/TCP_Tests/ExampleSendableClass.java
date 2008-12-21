import java.util.ArrayList;


public class ExampleSendableClass implements Sendable {
	int integ;
	double doubl;
	String stri;
	
	public ExampleSendableClass(int inte, double doub, String str)
	{
		integ = inte;
		doubl = doub;
		stri = str;
	}
	
	//This is the constructor that deals with the ArrayList<String> returned by toSendStringList()
	public ExampleSendableClass(ArrayList<String> str)
	{
		if (str.size() != 3)
			throw new IllegalArgumentException();
		
		integ = Integer.parseInt(str.get(0));
		doubl = Double.parseDouble(str.get(1));
		stri = str.get(2);
	}
	
	//This is the method that returns an arraylist of strings which can be used to
	//recreate this object
	public ArrayList<String> toSendStringList() {
		ArrayList<String> ret = new ArrayList<String>();
		
		ret.add(""+integ);
		ret.add(""+doubl);
		ret.add(stri);
		
		return ret;
	}
	
	public boolean equals(Object obj)
	{
		ExampleSendableClass other = (ExampleSendableClass)obj;
		
		if (integ == other.integ && doubl == other.doubl && stri.equals(other.stri))
			return true;
		
		return false;
	}
}
