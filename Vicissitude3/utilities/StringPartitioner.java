package utilities;

/*
 * separates a string into an array of strings, breaks at specified character d
 */

public class StringPartitioner
{
	String[] strings;
	
	public StringPartitioner(){}
	public String[] breakString(String s, char d)
	{
		/*
		 * breaks into strings delineated by char d
		 */
		
		//System.out.println("break string called");
		strings = new String[10];
		//s = s.trim();
		s+=" ";
		char[] c = s.toCharArray();
		int off1 = 0;
		int off2 = -1;
		boolean firstWord = true;
		for(int i = 0; i < c.length; i++)
		{
			//System.out.println("i = "+i+", "+c[i]);
			if(c[i] == d && firstWord && off2 == -1)
			{
				//System.out.println("here1");
				off1 = i;
				off2 = -3;
				addToStringArray(createString(c, off1, off2));
				//System.out.println("first word found, "+off1+", "+off2);
				firstWord = false;
			}
			else if(c[i] == d && !firstWord && off2 == -2)
			{
				off1 = i;
				off2 = -3;
				//System.out.println("here2, "+off1+", "+off2);
			}
			else if(c[i] == d && !firstWord && off2 == -3)
			{
				off2 = i;
				//System.out.println("here3, "+off1+", "+off2);
				addToStringArray(createString(c, off1, off2));
				off1 = off2;
				off2 = -3;
			}
		}
		
		for(int i = 0; i < strings.length; i++)
		{
			if(strings[i] != null)
			{
				strings[i] = strings[i].trim();
			}
		}
		return strings;
	}
	private String createString(char[] c, int off1, int off2)
	{
		String s = new String("");
		if(off2 > 0)
		{
			//System.out.println("index = "+(off1+1));
			//System.out.println("end = "+(off2 - off1));
			for(int i = off1+1; i < (off1+1)+(off2 - off1); i++)
			{
				s+=c[i];
			}
		}
		else
		{
			for(int i = 0; i < off1; i++)
			{
				s+=c[i];
			}
		}
		return s;
	}
	private void addToStringArray(String s)
	{
		boolean added = false;
		for(int i = 0; i < strings.length; i++)
		{
			if(strings[i] == null)
			{
				strings[i] = s;
				added = true;
				//System.out.println("string added, "+s);
				break;
			}
		}
		if(!added)
		{
			enlargeStringArray();
			addToStringArray(s);
		}
	}
	private void enlargeStringArray()
	{
		String[] temp = new String[strings.length+1];
		for(int i = 0; i < strings.length; i++)
		{
			temp[i] = strings[i];
		}
		strings = temp;
	}
}
